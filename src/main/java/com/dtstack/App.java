package com.dtstack;

import com.dtstack.builder.SoapBuilder;
import com.dtstack.builder.SoapOperation;
import com.dtstack.assemble.RequestSoapMessage;
import com.dtstack.builder.core.Wsdl;
import com.dtstack.moudule.vo.SelectNode;
import com.dtstack.moudule.vo.SoapRequestParam;
import com.dtstack.net.ConnetFactory;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class App {
    private static String rb = "";

    static {
        rb = "静态资源";
    }

    public static void main(String[] args) {

        System.out.println(rb);
//        String wsdlUrl = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl";
//        String namespace = "http://WebXml.com.cn/";
//        String soapBinding = "WeatherWSSoap";
//        String method = "getRegionCountry";
//        String bodyJson = "{\"theRegionCode\":\"sdf\"}";
//        SelectNode selectNode = new SelectNode("","getSupportCityDataset","", bodyJson);
        String wsdlUrl = "http://172.39.8.10:8013/Monitor/M_QueryFacilityInfo?wsdl";
//        String namespace = "http://www.srrc.org.cn";
        String soapBinding = "M_QueryFacilityInfoImplServiceSoapBinding";
//        String method = "M_QueryFacilityInfo";
        String headerJson = "{\"BizKey\":\"BizKey-01\"}";
        String bodyJson = "{\n" +
                "\t\"param\": {\n" +
                "\t\t\"mfid\": \"51010001119999\",\n" +
                "\t\t\"equid\": \"51010001119999\",\n" +
                "\t\t\"docmfid\": {\n" +
                "\t\t\t\"docmfid\": \"51010001119999\",\n" +
                "\t\t\t\"docequid\": \"51010001119999\",\n" +
                "\t\t\t\"docmfid01\": {\n" +
                "\t\t\t\t\"docmfid01\": \"51010001119999\",\n" +
                "\t\t\t\t\"docequid01\": \"51010001119999\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"param2\": {\n" +
                "\t\t\"mfidparam2\": \"51010001119999param2\",\n" +
                "\t\t\"equidparam2\": \"51010001119999param2\",\n" +
                "\t\t\"docmfidparam2\": {\n" +
                "\t\t\t\"docmfidparam2\": \"51010001119999param2\",\n" +
                "\t\t\t\"docequidparam2\": \"51010001119999param2\",\n" +
                "\t\t\t\"docmfid01param2\": {\n" +
                "\t\t\t\t\"docmfid01param2\": \"51010001119999param2\",\n" +
                "\t\t\t\t\"docequid01param2\": \"51010001119999param2\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        SelectNode selectNode = new SelectNode(headerJson, bodyJson);

        try {
            String respones = ConnetFactory.getInstance().getSoapResponesBody(new SoapRequestParam(selectNode, wsdlUrl, soapBinding, "", ""), wsdlUrl);
            System.out.println("生成的请求报文xml为: \n" + respones);
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Hello World~~!");
    }

    private static String buildXmlMessage(String wsdlUrl,
                                          String namespace,
                                          String soapBinding,
                                          String method) {
        Objects.requireNonNull(wsdlUrl);
        Wsdl parse = Wsdl.parse(wsdlUrl);
        SoapBuilder builder = parse.binding().name("{" + namespace + "}" + soapBinding).find();
        for (SoapOperation operation : builder.getOperations()) {
            System.out.println(operation.getSoapAction());
            if (operation.getOperationName().equals(method)) {
                String inputMessage = builder.buildInputMessage(operation);
                return inputMessage;
            }
        }
        return "";
    }
}
