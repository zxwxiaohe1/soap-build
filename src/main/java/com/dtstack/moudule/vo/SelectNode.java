package com.dtstack.moudule.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaohe
 * @description: 值替换选取参数
 * @date 2020/3/20 14:31
 */
@Setter
@Getter
public class SelectNode {

    /**
     * 设置参数选取的header父节点
     */
    private String header = "MonitorHeader";
    /**
     * 设置参数选取的body父节点
     */
    private String body = "requestbody";
    /**
     * 请求头参数JSON
     */
    private String headerJson;

    /**
     * 参数JSON格式
     */
    private String bodyJson;

    public SelectNode() {
    }

    public SelectNode(String headerJson, String bodyJson) {
        this.headerJson = headerJson;
        this.bodyJson = bodyJson;
    }

    public SelectNode(String header, String body, String headerJson, String bodyJson) {
        this.header = header;
        this.body = body;
        this.headerJson = headerJson;
        this.bodyJson = bodyJson;
    }
}
