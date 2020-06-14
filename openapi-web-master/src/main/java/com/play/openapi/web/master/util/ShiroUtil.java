package com.play.openapi.web.master.util;

import com.play.openapi.web.master.pojo.AdminUser;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static AdminUser getCurrentUser() {
        AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        System.err.println(adminUser.getId()+"---------->"+adminUser.getStatus()+"------------->"+adminUser.getEmail());
        return  adminUser;
    }

    public static int getUserId() {
        return getCurrentUser().getId();
    }


}
