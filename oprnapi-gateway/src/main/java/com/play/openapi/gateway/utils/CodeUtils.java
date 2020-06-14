package com.play.openapi.gateway.utils;

public interface CodeUtils {
    int SUCCESS = 1;//成功
    int ERROR = 0;//成功
    int SIGNATURERROR = 10001;//签名校验失败
    int ACCESS_TOKEN_ERROR = 10002;//access_token校验失败
    int NETWORKERROR = 10003;//网络异常
    String ACCESS_TOKENKEY = "access_token";//ACCESS_TOKENKEY的请求参数名

}
