package com.dtstack.net;

import com.dtstack.assemble.RequestSoapMessage;
import com.dtstack.moudule.vo.SoapRequestParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author xiaohe
 * @description: 请求获取响应报文
 * @date 2020/3/20 11:37
 */
public class ConnetFactory {

    private static ConnetFactory singleInstance = null;
    private static Object lock = new Object();
    private final static Logger log = Logger.getLogger(ConnetFactory.class);

    /**
     * 返回单列对象
     *
     * @return
     */
    public static ConnetFactory getInstance() {
        if (singleInstance == null) {
            synchronized (lock) {
                if (singleInstance == null) {
                    singleInstance = new ConnetFactory();
                }
            }
        }
        return singleInstance;
    }

    /**
     * 获得soap响应报文
     *
     * @param soapRequestParam
     * @param url
     * @return
     * @throws SOAPException
     * @throws TransformerException
     * @throws UnsupportedEncodingException
     */
    public String getSoapResponesBody(SoapRequestParam soapRequestParam, String url) throws SOAPException,
            TransformerException, UnsupportedEncodingException {
        return getSoapResponesBody(createSoapMessage(soapRequestParam), url);
    }

    /**
     * 获得soap响应报文
     *
     * @param soapMessage
     * @param url
     * @return
     * @throws SOAPException
     * @throws TransformerException
     * @throws UnsupportedEncodingException
     */
    public String getSoapResponesBody(SOAPMessage soapMessage, String url) throws SOAPException,
            TransformerException, UnsupportedEncodingException {

        if (soapMessage == null) {
            return null;
        }
        try {
            soapMessage.writeTo(System.out);
        } catch (IOException e) {
            log.error("failed to write reqest soap text!", e);
        }
        //实例化一个soap连接对象工厂
        SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
        //实例化一个连接对象
        SOAPConnection connection = null;
        SOAPMessage reply = null;
        try {
            log.info("soap request url: " + url);
            connection = soapConnFactory.createConnection();
            reply = connection.call(soapMessage, getUrl(url));
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        //这部分的处理就是将返回的值转换为字符串的格式，也就是流和串之间的转换
        Source source = reply.getSOAPPart().getContent();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        ByteArrayOutputStream myOutStr = new ByteArrayOutputStream();
        StreamResult res = new StreamResult();
        res.setOutputStream(myOutStr);
        transformer.transform(source, res);
        //针对于汉子的编码格式，需要自己制定
        return myOutStr.toString(StandardCharsets.UTF_8.name());
    }

    /**
     * 创建soap请求对象
     *
     * @param soapRequestParam
     * @return
     * @throws SOAPException
     */
    private SOAPMessage createSoapMessage(SoapRequestParam soapRequestParam) {

        if (soapRequestParam == null) {
            return null;
        }
        MimeHeaders headers = new MimeHeaders();
        if (StringUtils.isNotBlank(soapRequestParam.getAuthName())
                && StringUtils.isNotBlank(soapRequestParam.getAuthPassword())) {
            String authorization = new BASE64Encoder().encode(soapRequestParam.getAuthPassword().getBytes());
            headers.setHeader("Authorization", "Basic " + authorization);
            headers.setHeader("SOAPAction", "");
            log.info("auth info " + soapRequestParam.getAuthName() + ":" + soapRequestParam.getAuthPassword());
        }
        RequestSoapMessage requestSoapMessage = new RequestSoapMessage();
        String reqBody = "";
        if (Objects.isNull(soapRequestParam.getSelectNode())) {
            reqBody = requestSoapMessage.buildXmlMessage(soapRequestParam);
        } else {
            reqBody = requestSoapMessage.buildRealInputMessage(soapRequestParam);
        }
        try {
            return MessageFactory.newInstance().createMessage(headers, new ByteArrayInputStream(reqBody.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("create SOAPMessage error " + e.getMessage());
        }
        return null;
    }

    /**
     * 设置连接超时时间
     *
     * @param urlStr String
     * @return URL
     */
    private synchronized URL getUrl(String urlStr) {
        URL curl = null;
        try {
            String address = urlStr;
            String path = "";
            if (urlStr.matches(".*:[0-9]\\d*.*")) {
                String[] strs = urlStr.split(":[0-9]\\d*");
                if (strs.length > 1) {
                    address = urlStr.substring(0, urlStr.length() - strs[1].length());
                    path = strs[1];
                }
            }
            curl = new URL(new URL(address), path, new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    log.info("request address " + url.toString());
                    URL target = new URL(url.toString());
                    URLConnection connection = target.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    return (connection);
                }
            });
        } catch (MalformedURLException e) {
            log.error("address object creation exception {}", e);
        }
        return curl;
    }
}
