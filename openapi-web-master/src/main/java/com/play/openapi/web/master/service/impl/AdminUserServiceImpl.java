package com.play.openapi.web.master.service.impl;

import com.play.openapi.web.master.mapper.AdminUserMapper;
import com.play.openapi.web.master.pojo.AdminUser;
import com.play.openapi.web.master.service.AdminUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser findUser( String email ) {
        return adminUserMapper.selectByEmail(email);
    }
}
