package com.play.openapi.web.master.service.impl;

import com.play.openapi.web.master.mapper.RoleMapper;
import com.play.openapi.web.master.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Override
    public List <String> findRoleByUid( int uid ) {
        return roleMapper.findRoleByUid(uid);
    }
}
