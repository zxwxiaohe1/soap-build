package com.dtstack.moudule.vo;


import com.dtstack.moudule.util.ConstantUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaohe
 * @description: Soap请求参数
 * @date 2020/3/20 11:43
 */
@Setter
@Getter
public class SoapRequestParam {

    /**
     * wsdl 地址，?wsdl结尾
     */
    private String wsdlUrl;

    /**
     * 命名空间
     */
    private String namespace;
    /**
     * 绑定子域
     */
    private String soapBinding;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 认证名
     */
    private String authName;
    /**
     * 认证密码
     */
    private String authPassword;

    /**
     * 参数
     */
    private SelectNode selectNode;

    public SoapRequestParam() {
        this.namespace = ConstantUtils.SOAP_DEFAULT_NAMESPACE;
    }

    public SoapRequestParam(String wsdlUrl, String authName, String authPassword) {
        this.wsdlUrl = wsdlUrl;
        this.authName = authName;
        this.authPassword = authPassword;
        this.namespace = ConstantUtils.SOAP_DEFAULT_NAMESPACE;
        setSplitMethod(this.wsdlUrl);
    }

    public SoapRequestParam(SelectNode selectNode, String wsdlUrl, String authName, String authPassword) {
        this.selectNode = selectNode;
        this.wsdlUrl = wsdlUrl;
        this.authName = authName;
        this.authPassword = authPassword;
        this.namespace = ConstantUtils.SOAP_DEFAULT_NAMESPACE;
        setSplitMethod(this.wsdlUrl);
    }

    public SoapRequestParam(String wsdlUrl, String soapBinding, String authName, String authPassword) {
        this.wsdlUrl = wsdlUrl;
        this.soapBinding = soapBinding;
        this.authName = authName;
        this.authPassword = authPassword;
        this.namespace = ConstantUtils.SOAP_DEFAULT_NAMESPACE;
        setSplitMethod(this.wsdlUrl);
    }

    public SoapRequestParam(SelectNode selectNode, String wsdlUrl, String soapBinding, String authName, String authPassword) {
        this.selectNode = selectNode;
        this.wsdlUrl = wsdlUrl;
        this.soapBinding = soapBinding;
        this.authName = authName;
        this.authPassword = authPassword;
        this.namespace = ConstantUtils.SOAP_DEFAULT_NAMESPACE;
        setSplitMethod(this.wsdlUrl);
    }

    public SoapRequestParam(String wsdlUrl, String namespace, String soapBinding, String authName, String authPassword) {
        this.wsdlUrl = wsdlUrl;
        this.namespace = namespace;
        this.soapBinding = soapBinding;
        this.authName = authName;
        this.authPassword = authPassword;
    }

    public SoapRequestParam(SelectNode selectNode, String wsdlUrl, String namespace, String soapBinding, String authName, String authPassword) {
        this.selectNode = selectNode;
        this.wsdlUrl = wsdlUrl;
        this.namespace = namespace;
        this.soapBinding = soapBinding;
        this.authName = authName;
        this.authPassword = authPassword;
    }

    public SoapRequestParam(String wsdlUrl, String namespace, String soapBinding, String method, String authName, String authPassword) {
        this.wsdlUrl = wsdlUrl;
        this.namespace = namespace;
        this.soapBinding = soapBinding;
        this.method = method;
        this.authName = authName;
        this.authPassword = authPassword;
    }

    public SoapRequestParam(SelectNode selectNode, String wsdlUrl, String namespace, String soapBinding, String method, String authName, String authPassword) {
        this.selectNode = selectNode;
        this.wsdlUrl = wsdlUrl;
        this.namespace = namespace;
        this.soapBinding = soapBinding;
        this.method = method;
        this.authName = authName;
        this.authPassword = authPassword;
    }

    public void setSplitMethod(String wsdlUrl) {
        String str = wsdlUrl.split(ConstantUtils.CHAR_COLON_QUESTION_MARK)[0];
        this.method = str.substring(str.lastIndexOf(ConstantUtils.CHAR_COLON_PATH_SEPARATOR) + 1);
    }
}
