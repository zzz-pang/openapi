package com.play.openapi.web.master.feign.client;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Component
public class SearchServiceFallback implements SearchService {
    @Override
    public List<Map> find(String json) throws IOException {
        return null;
    }

    @Override
    public long count(String json) throws IOException {
        return 0;
    }
}
