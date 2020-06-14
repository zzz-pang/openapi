package com.play.openapi.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.play.openapi.gateway.constants.SystemParameterConstants;
import com.play.openapi.gateway.exception.ExceptionDict;
import com.play.openapi.gateway.exception.GatewayException;
import com.play.openapi.gateway.utils.DateUtils;
import com.play.openapi.gateway.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * 校验请求是否携带公共请求参数
 */
@Component
public class SysParamFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //1.请求上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        Date date = new Date();
        context.put("receiveTime",date);
        //method
        //app_key
        //时间戳
        //签名

        try {
            validateMethod(request);
            validateAppKey(request);
            validateSign(request);
            validateTime(request);
        } catch (GatewayException e) {
            System.err.println(e.getMsg());

            context.getResponse().setContentType("text/html;charset=utf-8");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
            R r = new R().put("code", e.getCode()).put("msg", e.getMsg());

            String json = JSON.toJSONString(r);
            context.setResponseBody(json);

        }
        return null;
    }

    private void validateMethod( HttpServletRequest request ) {
        String method = request.getParameter(SystemParameterConstants.METHOD);

        if (StringUtils.isBlank(method)) {
            throw new GatewayException(ExceptionDict.ERROR_PARAM, "method的参数不能为空");

        }
    }

    private void validateAppKey( HttpServletRequest request ) {
        String method = request.getParameter(SystemParameterConstants.APP_KEY);

        if (StringUtils.isBlank(method)) {
            throw new GatewayException(ExceptionDict.ERROR_PARAM, "app_key的参数不能为空");

        }
    }

    private void validateSign( HttpServletRequest request ) {
        String method = request.getParameter(SystemParameterConstants.SIGN);

        if (StringUtils.isBlank(method)) {
            throw new GatewayException(ExceptionDict.ERROR_PARAM, "sign的参数不能为空");

        }
    }

    private void validateTime( HttpServletRequest request ) {
        String timestamp = request.getParameter(SystemParameterConstants.TIMESTAMP);

        if (StringUtils.isBlank(timestamp)) {
            throw new GatewayException(ExceptionDict.ERROR_PARAM, "TIMESTAMP(时间戳）的参数不能为空");

        }
        //用户传过来的时间戳  相差十分钟
        Date date = DateUtils.getDate(timestamp, "yyyy-MM-dd hh:mm:ss");

        if (!DateUtils.compareTimeStamp(DateUtils.addDate(date, 1000, 12), new Date())
        || !DateUtils.compareTimeStamp(DateUtils.addDate(new Date(),1000,12),date)) {
            throw new GatewayException(ExceptionDict.ERROR_PARAM, "调用超时");

        }
    }
}
