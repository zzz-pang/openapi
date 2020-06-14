package com.play.openapi.web.master.controller;

import com.play.openapi.web.master.pojo.AdminUser;
import com.play.openapi.web.master.util.R;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @RequestMapping("/doLogin")
    //@RequestBody  json字符串 ---> java对象
    public R login( @RequestBody AdminUser adminUser ) {
        System.out.println(adminUser);
//        HashMap map = new HashMap();
//        map.put("code",0);
//        map.put("msg","");
//        return  map;
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(adminUser.getEmail(),
                    adminUser.getPassword());

            Subject subject = SecurityUtils.getSubject();

            //登录
            subject.login(usernamePasswordToken);

            System.out.println(subject.isPermitted("sys:menu:add"));

            return R.ok();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage() + "------" + e.getLocalizedMessage());
            return R.error().put("errorMsg", e.getMessage());
        }

    }
}
