package com.play.openapi.web.master.config;

import com.play.openapi.web.master.shrio.realm.UsersRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //sessionManager
    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //默认是30分钟
        sessionManager.setGlobalSessionTimeout(1000*60*60*24);
        sessionManager.setSessionValidationSchedulerEnabled(true);

        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return  sessionManager;
    }

    //securityManager
    @Bean(name = "securityManager")
    public SecurityManager securityManager(UsersRealm usersRealm,SessionManager sessionManager){

        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setSessionManager(sessionManager);
        webSecurityManager.setRealm(usersRealm);

        //记住我
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        Cookie cookie = cookieRememberMeManager.getCookie();

        cookie.setMaxAge(60*60*24*3);
        cookie.setPath("/");

        webSecurityManager.setRememberMeManager(cookieRememberMeManager);


        return  webSecurityManager;
    }

    //shiroFilter
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //登录页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //没权限
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.json");

        // /xxx/=anon
        // /**=authc
        //什么map能保证顺序（存取顺序）？？
        Map<String, String> map = new LinkedHashMap();
        map.put("/public/**","anon");
        map.put("/doLogin","anon");
        map.put("/json/**","anon");
        map.put("/**","user");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return  shiroFilterFactoryBean;
    }


    // realm
    //注解生效    @RequirePermission("sys:user:add")
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return  new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);//底层创建代理对象使用cglib
        return  defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){

        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);

        return  advisor;

    }

}
