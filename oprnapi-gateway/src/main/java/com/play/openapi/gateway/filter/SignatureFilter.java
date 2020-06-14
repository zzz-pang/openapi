package com.play.openapi.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.play.openapi.gateway.constants.SystemParameterConstants;
import com.play.openapi.gateway.feign.cache.CacheService;
import com.play.openapi.gateway.utils.Md5Util;
import com.play.openapi.gateway.utils.R;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名过滤器
 */
@Component
public class SignatureFilter extends ZuulFilter {

    @Resource
    private CacheService cacheService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return context.sendZuulResponse();
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        //得到除sign外的所有key-value按照key排序的集合
        Map <String, String> map = gateParamTreeMap(request);
        //计算签名
        String appkey = request.getParameter(SystemParameterConstants.APP_KEY);
        String key = SystemParameterConstants.CACHE_APPKEY + "" + appkey;
        Map <String, String> appMap = cacheService.hGetAll(key);
        if (appMap != null) {
            //存储app_key 记录日志的过滤器需要
            context.put("appKey",appkey);

            String appSecret = appMap.get("appSecret");

            String sign = Md5Util.md5Signature(map, appSecret);
            String signParam = request.getParameter("sign");
            System.out.println(sign + "---------" + signParam);
            if (!sign.equals(signParam)) {
                context.getResponse().setContentType("text/html;charset=utf-8");
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                R r = new R().put("msg", "签名校验失败");

                String json = JSON.toJSONString(r);
                context.setResponseBody(json);
            }
        } else {
            context.getResponse().setContentType("text/html;charset=utf-8");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            R r = new R().put("msg", "请检查您的app_key是否正确");

            String json = JSON.toJSONString(r);
            context.setResponseBody(json);
        }


        return null;
    }

    private Map gateParamTreeMap( HttpServletRequest request ) {

        //key value
        Map <String, String> map = new TreeMap <>();
        Enumeration <String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (!key.equals(SystemParameterConstants.SIGN)) {
                String value = request.getParameter(key); //method app_key timestamp
                map.put(key, value);
            }

        }
        return map;
    }
}
