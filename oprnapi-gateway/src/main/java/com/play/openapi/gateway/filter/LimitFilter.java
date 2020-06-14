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
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 限制商家调用次数
 */
@Component
public class LimitFilter extends ZuulFilter {
    @Resource
    private CacheService cacheService;
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
    @Override
    public int filterOrder() {
        return 2;
    }
    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().sendZuulResponse();
    }
    /**
     - 得到app调用次数
     - 得到开放的服务的api的调用次数限制
     - @return
     - @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext =    RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //得到限定次数
        String  key = SystemParameterConstants.CACHE_APPKEY+""+request.getParameter(SystemParameterConstants.APP_KEY); //APPKEY:561AC1A8676CFCB0CC61B041AE42ABB8
        Map<String,String> appMap =  cacheService.hGetAll(key);
        long limit = Long.parseLong( appMap.get("limit"));
        //  得到app调用次数  redis 的  incr
        String countKey = SystemParameterConstants.CACHE_APPKEY_PV+request.getParameter(SystemParameterConstants.APP_KEY);
        System.err.println("---------------------------countKey"+countKey);
        long   count  = 0;
        if (!cacheService.exists(countKey)){
            //第一次调用
            cacheService.getAutoIncr(countKey);
            try{
                //设置过期时间（当天的23:59:59:999 过期）
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String  d = simpleDateFormat.format(date);//2020-6-14
                d = d+" 23:59:59:999";//2020-6-14 23:59:59:999
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
                Date endTime = simpleDateFormat1.parse(d);
                long time =  (date.getTime()-endTime.getTime())/1000;

                int t = time<1?SystemParameterConstants.LIMIT_ONE_DAY:((int)time+1);

                cacheService.expire(countKey,t);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            //不是第一次调用
            //得到调用了多少次
            count = Long.parseLong(cacheService.get(countKey));

            if (count>limit){
                requestContext.getResponse().setContentType("text/html;charset=utf-8");
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
                R r =  R.error("您已经超过限定次数");
                String json = JSON.toJSONString(r);
                requestContext.setResponseBody(json);
            }else{
                //不超过，次数加一
                cacheService.getAutoIncr(countKey);
            }

        }

        return null;
    }

}
