package com.play.openapi.web.master.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@FeignClient(name = "search-service", fallback = SearchServiceFallback.class)
public interface SearchService {


    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List <Map> find( @RequestParam(value = "json", required = true) String json ) throws IOException;

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public long count( @RequestParam(value = "json", required = true) String json ) throws IOException;


}
