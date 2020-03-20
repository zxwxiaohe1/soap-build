package com.dtstack.builder;

import javax.xml.namespace.QName;

/**
 * @author xiaohe
 * @date 2019/11/9
 */
public interface SoapOperation {

    QName getBindingName();

    String getOperationName();

    String getOperationInputName();

    String getOperationOutputName();

    String getSoapAction();

    boolean isRpc();

    boolean isInputSoapEncoded();

    boolean isOutputSoapEncoded();

}
