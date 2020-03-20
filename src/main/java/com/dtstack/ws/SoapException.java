package com.dtstack.ws;

/**
 * @author xiaohe
 * @date 2019/11/9
 */
public class SoapException extends RuntimeException {
    public SoapException(String s) {
        super(s);
    }

    public SoapException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SoapException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }
}
