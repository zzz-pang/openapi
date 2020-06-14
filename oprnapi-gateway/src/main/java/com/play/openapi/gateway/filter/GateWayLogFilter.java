package com.play.openapi.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.play.openapi.gateway.pojo.GateWayLogBean;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@Component
public class GateWayLogFilter extends ZuulFilter {

    //注入rabbitmq的模板对象
    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().sendZuulResponse();
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try {
            GateWayLogBean gateWayLogBean = new GateWayLogBean();

            Date receiveTime = (Date) context.get("receiveTime");
            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress().toString();

            gateWayLogBean.setApiName((String) context.get("apiName"));
            gateWayLogBean.setAppKey((String) context.get("appKey"));
            gateWayLogBean.setCreateTime(new Date());
            gateWayLogBean.setReceiveTime(receiveTime);
            gateWayLogBean.setErrorCode("0000");
            gateWayLogBean.setServIP(ip);
            gateWayLogBean.setVenderId(0);
            gateWayLogBean.setPlatformRepTime(System.currentTimeMillis() - receiveTime.getTime());
            gateWayLogBean.setRemoteIp(context.getRequest().getRemoteHost());
            gateWayLogBean.setRequestContent(context.getRequest().getParameter("param_json"));

            amqpTemplate.convertAndSend("gw_log", JSON.toJSONString(gateWayLogBean));
            System.out.println("日志发送成功");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }
}
