package com.dtstack.assemble;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtstack.builder.SoapBuilder;
import com.dtstack.builder.SoapOperation;
import com.dtstack.builder.core.Wsdl;
import com.dtstack.moudule.util.ConstantUtils;
import com.dtstack.moudule.vo.SoapRequestParam;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiaohe
 * @date 2019/11/9
 */
public class RequestSoapMessage {

    private final Logger log = Logger.getLogger(RequestSoapMessage.class);

    /**
     * 构建最终请求WebService的参数报文
     *
     * @param soapRequestParam
     * @return
     * @throws IOException
     */
    public String buildRealInputMessage(SoapRequestParam soapRequestParam) {

        checkHasLength(soapRequestParam.getWsdlUrl(), soapRequestParam.getNamespace(), soapRequestParam.getSoapBinding(), soapRequestParam.getMethod());
        String request = buildXmlMessage(soapRequestParam);
        if (StringUtils.isBlank(request)) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Document document = DocumentHelper.parseText(request);
            if (StringUtils.isNotBlank(soapRequestParam.getSelectNode().getHeaderJson())) {
                Element paramNode = document.getRootElement().element(ConstantUtils.TAG_SOAP_HEADER).element(soapRequestParam.getSelectNode().getHeader());
                replaceParamXml(paramNode, objectMapper.readTree(soapRequestParam.getSelectNode().getHeaderJson()));
            }
            if (StringUtils.isNotBlank(soapRequestParam.getSelectNode().getBodyJson())) {
                Element paramNode = document.getRootElement().element(ConstantUtils.TAG_SOAP_BODY).element(soapRequestParam.getSelectNode().getBody());
                paramNode.clearContent();
                JSONObject jsonObject = JSON.parseObject(soapRequestParam.getSelectNode().getBodyJson());
                replaceParamXml(paramNode, jsonObject);
            }
            return document.asXML();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 真实参数去替换生成的请求报文
     *
     * @param paramNode
     * @param jsonNode
     */
    private void replaceParamXml(Element paramNode, JsonNode jsonNode) {
        List<Element> elements = paramNode.elements();
        for (Element element : elements) {
            if (Objects.isNull(element.elements()) || element.elements().isEmpty()) {
                // 说明没有子集直接设值
                String jsonText = jsonNode.findPath(element.getName()).asText();
                element.setText(jsonText);
            } else {
                replaceParamXml(element, jsonNode);
            }
        }
    }

    /**
     * 真实参数去替换生成的请求报文
     *
     * @param paramNode
     * @param jsonObject
     */
    private void replaceParamXml(Element paramNode, JSONObject jsonObject) throws SOAPException {

        Map<String, String> cnode = new HashMap<>();
        Map<String, JSONObject> node = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            Object o = entry.getValue();
            if (o instanceof String) {
                cnode.put(entry.getKey(), String.valueOf(o));
            } else if (o instanceof JSONObject) {
                node.put(entry.getKey(), (JSONObject) o);
            }
        }
        if (!cnode.isEmpty()) {
            for (String ckey : cnode.keySet()) {
                paramNode.addElement(paramNode.getNamespacePrefix() + ConstantUtils.CHAR_COLON_ENGLISH + ckey).setText(cnode.get(ckey));
            }
        }
        if (!node.isEmpty()) {
            for (String nkey : node.keySet()) {
                paramNode.addElement(paramNode.getNamespacePrefix() + ConstantUtils.CHAR_COLON_ENGLISH + nkey);
                replaceParamXml(paramNode.element(nkey), node.get(nkey));
            }
        }
    }

    /**
     * 构建无参数的XML请求报文模板
     *
     * @param soapRequestParam
     * @return
     */
    public String buildXmlMessage(SoapRequestParam soapRequestParam) {
        Wsdl parse = Wsdl.parse(soapRequestParam.getWsdlUrl());
        SoapBuilder builder = parse.binding().name("{" + soapRequestParam.getNamespace() + "}" + soapRequestParam.getSoapBinding()).find();
        for (SoapOperation operation : builder.getOperations()) {
            if (operation.getOperationName().equals(soapRequestParam.getMethod())) {
                String inputMessage = builder.buildInputMessage(operation);
                return inputMessage;
            }
        }
        log.info("该Wsdl中找不到method:[" + soapRequestParam.getMethod() + "]方法");
        return "";
    }

    /**
     * 验证参数是否为空
     *
     * @param str
     */
    private void checkHasLength(String... str) {
        for (String s : str) {
            if (Objects.isNull(s) || s.length() == 0) {
                throw new IllegalArgumentException("调用WebService入参为空");
            }
        }
    }

}
