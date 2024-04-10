package com.lhm.client;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * @author : 李煌民
 * @date : 2021-06-21 09:50
 **/
public class EsHighQueryTest {
    RestHighLevelClient esClient = null;
    Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Before
    public void before() {
        esClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    /**
     * 查询全部的数据
     *
     * @throws IOException
     */
    @Test
    public void searchAll() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        request.source(query);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 条件查询
     *
     * @throws IOException
     */
    @Test
    public void searchByCondition() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.termQuery("name", "lisi"));
        request.source(query);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 分页查询
     * 起始位置的计算公式：(页码-1)* 每页数据条数
     *
     * @throws IOException
     */
    @Test
    public void limitSearch() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        //设置起始位置
        query.from(2);
        //设置每页的大小
        query.size(2);

        request.source(query);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 排序查询
     *
     * @throws IOException
     */
    @Test
    public void orderSearch() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        query.sort("_id", SortOrder.DESC);

        request.source(query);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 过滤字段查询
     *
     * @throws IOException
     */
    @Test
    public void filterSearch() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder query = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        String[] exclude = {};
        String[] include = {"name"};
        query.fetchSource(include, exclude);

        request.source(query);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 组合条件查询
     *
     * @throws IOException
     */
    @Test
    public void combinationSearch() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // where age = '12' and sex != '女'
        //boolQueryBuilder.must(QueryBuilders.matchQuery("age", 12));
        //boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "女"));

        //where age = '12' or age = '13'
        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 12));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 13));

        SearchSourceBuilder query = searchSourceBuilder.query(boolQueryBuilder);

        request.source(query);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 范围查询
     *
     * @throws IOException
     */
    @Test
    public void rangeSearch() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQueryBuild = QueryBuilders.rangeQuery("age");

        //where age >= 12 and age <= 14
        rangeQueryBuild.gte(12);
        rangeQueryBuild.lte(14);

        SearchSourceBuilder query = searchSourceBuilder.query(rangeQueryBuild);

        request.source(query);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 模糊查询
     *
     * @throws IOException
     */
    @Test
    public void fuzzySearch() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //Fuzziness.ONE表示差一个字符也可以匹配出来
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "wangwu").fuzziness(Fuzziness.TWO);

        SearchSourceBuilder query = searchSourceBuilder.query(fuzzyQueryBuilder);

        request.source(query);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 高亮查询
     *
     * @throws IOException
     */
    @Test
    public void highLightSearch() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhangsan");

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");

        SearchSourceBuilder query = searchSourceBuilder.query(termQueryBuilder);

        request.source(query);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 聚合查询
     *
     * @throws IOException
     */
    @Test
    public void maxSearch() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MaxAggregationBuilder myAggregation = AggregationBuilders.max("maxAge").field("age");
        searchSourceBuilder.aggregation(myAggregation);

        request.source(searchSourceBuilder);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 分组查询
     *
     * @throws IOException
     */
    @Test
    public void groupSearch() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("user");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        TermsAggregationBuilder groupByField = AggregationBuilders.terms("groupbyAge").field("age");
        searchSourceBuilder.aggregation(groupByField);

        request.source(searchSourceBuilder);

        SearchResponse search = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();

        System.out.println(hits.getTotalHits());
        System.out.println(search.getTook());

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
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
