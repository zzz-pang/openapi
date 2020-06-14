package com.play.openapi.monitor.feign.client;

import com.play.openapi.monitor.feign.client.impl.SearchServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;


@FeignClient(name = "search-service", fallback = SearchServiceFallback.class)
public interface SearchService {

    @RequestMapping(value = "/statAvg", method = RequestMethod.GET)
    public Map <String, Object> statAvg( @RequestParam(value = "startTime", required = true) long startTime, @RequestParam(value = "endTime", required = true) long endTime ) throws IOException;

}
