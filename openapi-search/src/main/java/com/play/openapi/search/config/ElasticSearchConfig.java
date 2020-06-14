package com.play.openapi.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticSearchConfig {

    private static final String SCAHEME = "http";

    @Value("${spring.data.elasticsearch.host}")
    private String host;
    @Value("${spring.data.elasticsearch.port}")
    private int port;

    private RestClientBuilder restClientBuilder;
    private static List <HttpHost> httpHostList;

    static {
        httpHostList = new ArrayList <>();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        String s[] = host.split(",");
        for (String s1 : s) {
            HttpHost httpHost = new HttpHost(s1, port, SCAHEME);
            httpHostList.add(httpHost);
        }
//        list-array
        restClientBuilder = RestClient.builder(httpHostList.toArray(new HttpHost[0]));

        return new RestHighLevelClient(restClientBuilder);
    }

}
