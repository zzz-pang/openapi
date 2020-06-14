package com.play.openapi.gateway.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GateWayLogBean {
    private  String   appKey;
    private  String   servIP;
    private  long   venderId;
    private  String   remoteIp;
    private  String   apiName;
    private  long   platformRepTime;
    private  String   requestContent;
    private  String   errorCode;
    private Date receiveTime;
    private Date createTime;
}
