package com.dtstack.legacy;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
/**
 * @author xiaohe
 * @date 2019/11/9
 */
public interface SchemaLoader {
    XmlObject loadXmlObject(String wsdlUrl, XmlOptions options) throws Exception;

    String getBaseURI();
}
