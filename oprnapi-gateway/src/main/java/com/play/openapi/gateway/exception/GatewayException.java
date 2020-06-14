package com.play.openapi.gateway.exception;


public class GatewayException extends BaseException {
    public GatewayException(String code, String msg) {
        super(code,msg);
    }
}

