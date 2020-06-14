package com.play.openapi.gateway.exception;

public class ExceptionDict {

    //D成功
    public static final String SUCCESS = "1-0000";

    //参数错误
    public static final String ERROR_PARAM = "1-0001";

    //签名校验失败
    public static final String SIGNATURERROR = "1-0002";

    //access_token校验失败
    public static final String ACCESS_TOKEN_ERROR = "1-0003";

    //   描述（英文） App Call Limited 描述（中文） 限制时间内调用失败次数
    public static final String LIMIT_ERROR = "1-0005";

    //   描述（英文） App Call Limited 描述（中文） 限制时间内调用失败次数
    public static final String ROUTE_ERROR = "1-0006";
    //网络异常
    public static final String NETWORKERROR = "1-0007";


}
