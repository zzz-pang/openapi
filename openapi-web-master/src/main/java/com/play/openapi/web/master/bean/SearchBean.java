package com.play.openapi.web.master.bean;

import lombok.Data;

@Data
public class SearchBean {
    private String apiName;
    private String requestContent;
    private Long startTime;
    private Long endTime;
    private int limit;
    private int offset;

}
