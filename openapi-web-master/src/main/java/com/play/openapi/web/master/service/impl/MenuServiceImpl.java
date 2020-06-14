package com.play.openapi.web.master.service.impl;

import com.play.openapi.web.master.mapper.MenuMapper;
import com.play.openapi.web.master.pojo.AdminUser;
import com.play.openapi.web.master.pojo.Menu;
import com.play.openapi.web.master.service.MenuService;
import com.play.openapi.web.master.util.R;
import com.play.openapi.web.master.util.ShiroUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public R menu() {
        int userId = ShiroUtil.getUserId();
        System.err.println("--------------->" + userId);
        //权限管理

        //目录
        List <Menu> dir = menuMapper.findDirByUid(userId);
        for (Menu menu : dir) {
            List <Menu> menus = menuMapper.findMenuByUid(userId, menu.getId());
            menu.setChildren(menus);
        }
        List <String> perms = findPerms();
        return R.ok().put("menuList", dir).put("permissions", perms);
    }

    @Override
    public List <String> findPerms() {
        int userId = ShiroUtil.getUserId();
        List <String> perms = menuMapper.findPerms(userId);
        List <String> list = new ArrayList <>();
        for (String perm : perms) {
            if (null != perm) {
                list.add(perm);
            }
        }
        return list;
    }
}
