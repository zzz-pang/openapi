package com.play.openapi.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.play.openapi.gateway.constants.SystemParameterConstants;
import com.play.openapi.gateway.feign.cache.CacheService;
import com.play.openapi.gateway.utils.R;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 路由过滤器，第三方平台（用户）能否调用api的控制
 */
@Component
public class RouterFilter extends ZuulFilter {

    @Resource
    private CacheService cacheService;

    @Override
    public String filterType() { //pre post error router
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 6; //执行顺序  值越小 执行越靠前
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        boolean b = context.sendZuulResponse();

        return b; //是否走该过滤器
    }

    @Override
    public Object run() throws ZuulException {
        //api 地址
        //1.得到请求上下文
        RequestContext context = RequestContext.getCurrentContext();

        //2.得到请求参数
        String method = context.getRequest().getParameter(SystemParameterConstants.METHOD);
        //3.判断是否提供了该api

        String key = SystemParameterConstants.CACHE_API + method;
        System.err.println(key);
        Map <String, String> map = cacheService.hGetAll(key);
        if (map == null || map.size() == 0) {
            //不存在该api
            context.getResponse().setContentType("text/html;charset=utf-8");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
            R r = R.error("路由失败，没有提供该api");
            String json = JSON.toJSONString(r);
            context.setResponseBody(json);
        } else {
            //存在
            System.err.println("存在" + map);
            //存储apiName 因为记录日志的过滤器需要
            context.put("apiName",method);
            //得到调用的服务名
            String serviceId = map.get("serviceId");
            //得到内部url的地址
            String insideApiUrl = map.get("insideApiUrl");
            //调用
            context.put(FilterConstants.SERVICE_ID_KEY, serviceId);
            context.put(FilterConstants.REQUEST_URI_KEY, insideApiUrl);
        }

        return null;
    }
}
