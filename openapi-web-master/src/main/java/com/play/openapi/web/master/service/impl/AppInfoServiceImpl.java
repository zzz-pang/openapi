package com.play.openapi.web.master.service.impl;

import com.play.openapi.web.master.mapper.AppInfoMapper;
import com.play.openapi.web.master.pojo.AppInfo;
import com.play.openapi.web.master.service.AppInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Resource
    private AppInfoMapper appInfoMapper;
    @Override
    public List <AppInfo> findAll() {
        return appInfoMapper.findAll();
    }
}
