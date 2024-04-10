package com.lhm.client;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author : 李煌民
 * @date : 2021-06-21 09:24
 **/
public class EsBatchOperTest {
    RestHighLevelClient esClient = null;
    Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Before
    public void before() {
        esClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    /**
     * 批量插入操作
     * @throws IOException
     */
    @Test
    public void insertBatch() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON,"name","zhangsan","age",12,"sex","男"));
        bulkRequest.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON,"name","lisi","age",13,"sex","男"));
        bulkRequest.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON,"name","wangwu","age",14,"sex","男"));
        bulkRequest.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON,"name","wangwu_1","age",15,"sex","男"));
        bulkRequest.add(new IndexRequest().index("user").id("1006").source(XContentType.JSON,"name","wangwu_2","age",16,"sex","男"));

        BulkResponse response = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        System.out.println(response.getTook());
        System.out.println(Arrays.toString(response.getItems()));
    }

    /**
     * 批量删除操作
     * @throws IOException
     */
    @Test
    public void deleteBatch() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        bulkRequest.add(new DeleteRequest().index("user").id("1001"));
        bulkRequest.add(new DeleteRequest().index("user").id("1002"));
        bulkRequest.add(new DeleteRequest().index("user").id("1003"));

        BulkResponse response = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        System.out.println(response.getTook());
        System.out.println(Arrays.toString(response.getItems()));
    }

    @After
    public void close() {
        try {
            esClient.close();
        } catch (IOException e) {
            log.error("close Exception");
            e.printStackTrace();
        }
    }
}
