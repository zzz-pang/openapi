package com.play.openapi.web.master.service;

import com.play.openapi.web.master.pojo.ApiMapping;
import com.play.openapi.web.master.util.R;

import java.util.List;

public interface GateWayService {
    R findGateWay( int limit, int offset, String gatewayApiName, Integer state );

    R add( ApiMapping apiMapping );

    R info( int id );

    R update( ApiMapping apiMapping );

    R delAll( List <Integer> list );

}
