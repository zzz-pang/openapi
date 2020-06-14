package com.play.openapi.search.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class SearchUtil {
    /**
     * 索引的设置
     *
     * @param request
     */
    public static void settings( CreateIndexRequest request ) {
        //number_of_replicas  number_of_shards
        request.settings(Settings.builder().put("index.number_of_replicas", 2));
        request.settings(Settings.builder().put("index.number_of_shards", 3));
    }

    /**
     * 创建映射关系
     * * PUT /qf/_mapping/student
     * * {
     * *   "properties": {
     * *     "name":{
     * *       "type": "text",
     * *       "analyzer": "ik_smart"
     * *     },
     * *     "desc":{
     * *       "store": true,
     * *       "analyzer": "ik_smart"
     * *     },
     * *     "sex":{
     * *       "store": true,
     * *       "analyzer": "ik_smart"
     * *     },
     * *     "age":{
     * *       "store": true
     * *     }
     * *   }
     * * }
     */
//                .field("format", "yyyy-MM-dd HH:mm:ss")
//                .endObject()
    public static void builderMapping( CreateIndexRequest request, String type ) throws IOException {
        XContentBuilder xContentBuilder = JsonXContent.contentBuilder();
        xContentBuilder.startObject()
                .startObject("properties")
                .startObject("appKey")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("servIP")
                .field("type", "ip")
                .field("index", "true")
                .endObject()

                .startObject("venderId")
                .field("type", "long")
                .field("index", "true")
                .endObject()

                .startObject("remoteIp")
                .field("type", "ip")
                .field("index", "true")
                .endObject()

                .startObject("apiName")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

//                .startObject("totalRepTime")
//                .field("type", "long")
//                .field("index", "true")
//                .endObject()

                .startObject("platformRepTime")
                .field("type", "long")
                .field("index", "true")
                .endObject()

                .startObject("requestContent")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .field("index", "true")
                .endObject()

                .startObject("errorCode")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("receiveTime")
                .field("type", "date")
                .field("index", "true")
//                .field("format", "yyyy-MM-dd HH:mm:ss")
                .endObject()

                .startObject("createTime")
                .field("type", "date")
                .field("index", "true")
                .endObject()
                .endObject()
                .endObject();

        request.mapping(type, xContentBuilder);

    }

    public static void searchBuilder( String jsonParam, SearchSourceBuilder searchSourceBuilder ){
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        JSONObject jsonObject=JSONObject.parseObject(jsonParam);
        //取出条件
        String requestContent = jsonObject.getString("requestContent");
        String appKey = jsonObject.getString("appKey");
        String apiName = jsonObject.getString("apiName");
        Long startTime = jsonObject.getLong("startTime");
        Long endTime = jsonObject.getLong("endTime");
        if (StringUtils.isNotBlank(requestContent)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("requestContent",requestContent));
        }
        if (StringUtils.isNotBlank(appKey)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("appKey",appKey));
        }
        if (StringUtils.isNotBlank(apiName)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("apiName",apiName));
        }
        if (startTime!=null&&endTime!=null){
            boolQueryBuilder.must(QueryBuilders.rangeQuery("receiveTime").gte(startTime).lte(endTime));
        }
        searchSourceBuilder.query(boolQueryBuilder);
    }

}
