package com.dtstack.ws;

/**
 * @author xiaohe
 * @date 2019/11/9
 */
public class SoapBuilderException extends SoapException{
    public SoapBuilderException(String message) {
        super(message);
    }

    public SoapBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoapBuilderException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
