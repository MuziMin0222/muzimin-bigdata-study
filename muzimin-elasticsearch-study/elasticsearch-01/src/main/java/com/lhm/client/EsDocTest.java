package com.lhm.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhm.bean.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * @author : 李煌民
 * @date : 2021-05-26 17:42
 **/
public class EsDocTest {
    RestHighLevelClient esClient = null;
    Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Before
    public void getClient() {
        esClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    /**
     * 文档数据的创建
     * @throws IOException
     */
    @Test
    public void createDoc() throws IOException {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user").id("1001");

        User user = new User();
        user.setName("lhm");
        user.setAge("100");
        user.setSex("男");

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        indexRequest.source(userJson, XContentType.JSON);

        IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(indexResponse.getResult());
        System.out.println(indexResponse.status());
    }

    /**
     * 文档数据的更新
     * @throws IOException
     */
    @Test
    public void updateDoc() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user").id("1001");
        updateRequest.doc(XContentType.JSON, "age", "21");

        UpdateResponse updateResponse = esClient.update(updateRequest, RequestOptions.DEFAULT);

        System.out.println(updateResponse.getResult());
    }

    /**
     * 获取文档数据
     * @throws IOException
     */
    @Test
    public void getDoc() throws IOException {
        GetRequest getRequest = new GetRequest();

        getRequest.index("user").id("1001");
        GetResponse response = esClient.get(getRequest, RequestOptions.DEFAULT);

        System.out.println(response.getSourceAsString());
    }

    /**
     * 删除文档数据
     * @throws IOException
     */
    @Test
    public void deleteDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest();

        deleteRequest.index("user").id("1001");
        DeleteResponse delete = esClient.delete(deleteRequest, RequestOptions.DEFAULT);

        System.out.println(delete.status());
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
