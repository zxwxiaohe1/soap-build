package com.dtstack.builder;

import com.dtstack.ws.SoapContext;
/**
 * @author xiaohe
 * @date 2019/11/9
 */
public interface SoapOperationFinder {

    SoapOperationFinder name(String operationName);

    SoapOperationFinder soapAction(String soapAction);

    SoapOperationFinder inputName(String inputName);

    SoapOperationFinder outputName(String inputName);

    SoapOperationBuilder find();

    SoapOperationBuilder find(SoapContext context);
}
