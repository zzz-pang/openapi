package com.play.openapi.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.play.openapi.search.exception.ExceptionDict;
import com.play.openapi.search.exception.SearchException;
import com.play.openapi.search.service.SearchService;
import com.play.openapi.search.util.SearchUtil;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Value("${spring.data.elasticsearch.indexName}")
    private String indexName;
    @Value("${spring.data.elasticsearch.type}")
    private String type;

    private Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void createIndex() throws IOException {
        if (!existIndex()) {
            //1.创建请求对象
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            //2、索引设置 分片规则和副本
            SearchUtil.settings(request);
            //3.索引和类型的映射关系
            SearchUtil.builderMapping(request, type);
            //4.创建索引
            CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            System.err.println(response.isAcknowledged());
        } else {
            throw new SearchException(ExceptionDict.existsError, "索引已存在");
        }
    }

    @Override
    public boolean existIndex() throws IOException {
        //创建请求对象
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(indexName);
        boolean f = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        return f;
    }

    @Override
    public boolean deleteIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return delete.isAcknowledged();
    }

    @Override
    public void saveData( String json ) throws IOException {
        //1.创建请求对象
        IndexRequest indexRequest = new IndexRequest(indexName, type);
        indexRequest.source(json, XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        logger.debug("add data index" + index.status());

    }

    @Override
    public boolean deleteData( String id ) throws IOException {
        return false;
    }

    @Override
    public List <Map> search( String json ) throws IOException {
        Map <String, Object> map = JSON.parseObject(json, Map.class);
        //1.创建查询对象
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(type);
        //2.创建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.matchAllQuery()); //全部查询
//        Object o = map.get("requestContent");
        //单个查询
//        if (null != o) {
//            searchSourceBuilder.query(QueryBuilders.matchQuery("requestContent", o));
//        }
        SearchUtil.searchBuilder(json, searchSourceBuilder);
        //分页
        Object s = map.get("start");
        if (null != s) {
            searchSourceBuilder.from(Integer.parseInt(s + ""));
        }
        Object rows = map.get("rows");
        if (null != rows) {
            searchSourceBuilder.size(Integer.parseInt(rows + ""));
        }
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("requestContent"); //高亮显示字段
        highlightBuilder.preTags("<em style=\"color:red;\">");
        highlightBuilder.postTags("</em>");

        searchSourceBuilder.highlighter(highlightBuilder);
        //3.指定查询条件
        searchRequest.source(searchSourceBuilder);
        //4.执行查询
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits searchHits = search.getHits();
        SearchHit searchHit[] = searchHits.getHits();

        List <Map> list = new ArrayList <>();
        for (SearchHit hit : searchHit) {
            Map <String, Object> sourceAsMap = hit.getSourceAsMap();
            System.err.println("--------->非高亮" + sourceAsMap);

            Map <String, HighlightField> map1 = hit.getHighlightFields();
            if (map1!=null){
                HighlightField highlightField = map1.get("requestContent");
                if (highlightField!=null){
                    String h = highlightField.getFragments()[0].toString();
                    sourceAsMap.put("requestContent", h);//把高亮显示的内容覆盖不高亮显示的部分
                }
            }

            list.add(sourceAsMap);
            System.err.println("------》高亮" + sourceAsMap);

            System.err.println(map);
        }

        return list;
    }

    @Override
    public Long count( String params ) throws IOException {
        SearchRequest request = new SearchRequest(indexName);
        request.types(type);
        //调用工具类中的查询条件
        SearchUtil.searchBuilder(params, SearchSourceBuilder.searchSource());

        //执行查询请求
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return response.getHits().getTotalHits();
    }

    @Override
    public Map statAvg( long startTime, long endTime ) throws IOException {
        SearchRequest request = new SearchRequest(indexName);
        request.types(type);
        //条件查询
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("receiveTime").gte(startTime).lte(endTime));

        //聚合查询
        //分组
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("apiName_group").field("apiName");
        //求调用的平均时长
        termsAggregationBuilder.subAggregation(AggregationBuilders.avg("avg_platformRepTime").field("platformRepTime"));

        searchSourceBuilder.aggregation(termsAggregationBuilder);

        //指定查询条件
        request.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();

        Terms terms = aggregations.get("apiName_group");
        Map map = new HashMap();
        List <? extends Terms.Bucket> list = terms.getBuckets();
        for (Terms.Bucket bucket : list) {
            Avg avg = bucket.getAggregations().get("avg_platformRepTime");
            System.err.println(bucket.getKey() + "----" + avg.getValue());
            map.put("avg", avg.getValue());
            map.put("apiName", bucket.getKey());
        }
        return map;
    }
}
