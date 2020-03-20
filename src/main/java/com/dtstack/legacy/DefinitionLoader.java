package com.dtstack.legacy;
/**
 * @author xiaohe
 * @date 2019/11/9
 */
public interface DefinitionLoader extends SchemaLoader {
    void setProgressInfo(String info);

    boolean isAborted();

    boolean abort();

    void setNewBaseURI(String uri);

    String getFirstNewURI();
}
