package com.play.openapi.web.master.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.play.openapi.web.master.feign.client.CacheService;
import com.play.openapi.web.master.mapper.ApiMappingMapper;
import com.play.openapi.web.master.pojo.ApiMapping;
import com.play.openapi.web.master.service.GateWayService;
import com.play.openapi.web.master.util.R;
import com.play.openapi.web.master.util.SysConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GateWayServiceImpl implements GateWayService {
    @Resource
    private ApiMappingMapper apiMappingMapper;
    @Resource
    private CacheService cacheService;

    private String name;

    @Override
    public R findGateWay( int limit, int offset, String gatewayApiName, Integer state ) {
        //分页工具类
        PageHelper.offsetPage(offset, limit);
        List <ApiMapping> list = apiMappingMapper.find(gatewayApiName, state);
        PageInfo <ApiMapping> pageInfo = new PageInfo <>(list);

        list = pageInfo.getList();
        //整合缓存
        for (int i = 0; i < list.size(); i++) {
            ApiMapping apiMapping = list.get(i);
            String key = SysConstant.API_KEY + apiMapping.getGatewayapiname();
            System.err.println("--------------------------->" + key);
            if (!cacheService.exists(key)) {
                System.out.println("--------------->插入缓存");
                Map map = new HashMap();
                map.put("serviceId", apiMapping.getServiceid());
                map.put("insideApiUrl", apiMapping.getInsideapiurl());
                cacheService.hmset(key, map);
            }

        }


        return new R().put("total", pageInfo.getTotal()).put("rows", list);
    }

    @Override
    public R add( ApiMapping apiMapping ) {
        int insert = apiMappingMapper.insert(apiMapping);
        if (insert > 0) {
            String key = SysConstant.API_KEY + apiMapping.getGatewayapiname();
            System.out.println("--------------->插入缓存");
            Map map = new HashMap();
            map.put("serviceId", apiMapping.getServiceid());
            map.put("insideApiUrl", apiMapping.getInsideapiurl());
            cacheService.hmset(key, map);
            return R.ok();
        }
        return R.error();
    }

    @Override
    public R info( int id ) {
        ApiMapping apiMapping = apiMappingMapper.selectByPrimaryKey(id);
        name=null;
        name=apiMapping.getGatewayapiname();
        return R.ok().put("api",apiMapping );
    }

    @Override
    public R update( ApiMapping apiMapping ) {
        int i = apiMappingMapper.updateByPrimaryKeySelective(apiMapping);
        if (i > 0) {
            String oldKey = SysConstant.API_KEY +name;
            System.out.println("oldkey-----------------------"+oldKey);
            String key = SysConstant.API_KEY + apiMapping.getGatewayapiname();
            cacheService.del(oldKey);
            System.out.println("new key-----------------"+key);
            Map map = new HashMap();
            map.put("serviceId", apiMapping.getServiceid());
            map.put("insideApiUrl", apiMapping.getInsideapiurl());
            cacheService.hmset(key, map);
            return R.ok();
        }

        return R.error();
    }

    @Override
    public R delAll( List <Integer> ids ) {
        List <ApiMapping> list = new ArrayList <>();
        //删除缓存
        for (Integer id : ids) {
            ApiMapping apiMapping = apiMappingMapper.selectByPrimaryKey(id);

            list.add(apiMapping);
        }
        int i = apiMappingMapper.delAll(ids);
        if (i > 0) {
            for (ApiMapping apiMapping : list) {
                String key = SysConstant.API_KEY + apiMapping.getGatewayapiname();
                cacheService.del(key);
            }

            return R.ok();
        }

        return R.error();
    }
}
