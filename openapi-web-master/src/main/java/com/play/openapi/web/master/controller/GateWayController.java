package com.play.openapi.web.master.controller;

import com.play.openapi.web.master.pojo.ApiMapping;
import com.play.openapi.web.master.service.GateWayService;
import com.play.openapi.web.master.util.R;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/gateway")
public class GateWayController {

    @Resource
    private GateWayService gateWayService;

    @RequestMapping(params = "act=table")
    /**
     * total:xxx
     * rows:[{},{}]
     */
    public R gateWayList( int limit, int offset, String gatewayApiName, Integer state ) {
        return gateWayService.findGateWay(limit, offset, gatewayApiName, state);
    }

    @RequestMapping(params = "act=add")
    public R add(@RequestBody ApiMapping apiMapping ) {
        System.out.println(apiMapping.getGatewayapiname()+"-------------"+apiMapping.getServiceid());
        System.err.println("新增--------------------------");
        return gateWayService.add(apiMapping);
    }

    @RequestMapping(params = "act=info")
    public R info( @RequestParam("id") int id ) {
        return gateWayService.info(id);
    }

    @RequestMapping(params = "act=update")
    public R update( @RequestBody ApiMapping apiMapping ) {
        return gateWayService.update(apiMapping);
    }

    @RequestMapping(params = "act=del")
    public R del( @RequestBody List <Integer> ids ) {
        return gateWayService.delAll(ids);
    }

}
