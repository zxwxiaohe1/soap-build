package com.dtstack.ws;
import javax.xml.namespace.QName;
import java.util.Set;

/**
 * @author xiaohe
 * @date 2019/11/9
 */
public interface SoapMultiValuesProvider {
    Set<String> getMultiValues(QName name);
}
