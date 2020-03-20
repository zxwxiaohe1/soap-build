package com.dtstack.builder;

import com.dtstack.ws.SoapContext;

import javax.xml.namespace.QName;

/**
 * @author xiaohe
 * @date 2019/11/9
 */
public interface SoapBuilderFinder {

    SoapBuilderFinder name(String name);

    SoapBuilderFinder name(QName name);

    SoapBuilderFinder namespaceURI(String namespaceURI);

    SoapBuilderFinder localPart(String localPart);

    SoapBuilderFinder prefix(String prefix);

    SoapBuilder find();

    SoapBuilder find(SoapContext context);
}
