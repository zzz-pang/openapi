package com.play.openapi.web.master.controller;

import com.play.openapi.web.master.service.MenuService;
import com.play.openapi.web.master.util.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MenuController {

    @Resource
    private MenuService menuService;

    @RequiresPermissions("sys:menu:list")
    @RequiresRoles("admin")
    @RequestMapping("/side")
    public R menuList() {
        return menuService.menu();
    }
}
