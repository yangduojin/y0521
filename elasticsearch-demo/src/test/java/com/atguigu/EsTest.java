package com.atguigu;

import com.alibaba.fastjson.JSON;
import com.atguigu.mapper.GoodsMapper;
import com.atguigu.pojo.Goods;
import com.atguigu.pojo.Person;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

    @Autowired
    private RestHighLevelClient yxClient;

    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void testDemo() throws Exception {
        IndicesClient indices = yxClient.indices();
        CreateIndexRequest creatRequest = new CreateIndexRequest("bbc");
        CreateIndexResponse response = indices.create(creatRequest, RequestOptions.DEFAULT);

        System.out.println(response.isAcknowledged());
        System.out.println(response.isShardsAcknowledged());
    }

    @Test
    public void testAddAndMappign() throws IOException {
        IndicesClient indices = yxClient.indices();

        CreateIndexRequest request = new CreateIndexRequest("aaa");
        String mapping = "{\n" +
                "      \"properties\" : {\n" +
                "        \"address\" : {\n" +
                "          \"type\" : \"text\",\n" +
                "          \"analyzer\" : \"ik_max_word\"\n" +
                "        },\n" +
                "        \"age\" : {\n" +
                "          \"type\" : \"long\"\n" +
                "        },\n" +
                "        \"name\" : {\n" +
                "          \"type\" : \"keyword\"\n" +
                "        }\n" +
                "      }\n" +
                "    }";
        request.mapping(mapping, XContentType.JSON);
        CreateIndexResponse response = indices.create(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @Test
    public void testQueryIndex() throws Exception{
        IndicesClient indices = yxClient.indices();
        GetIndexRequest getRequest = new GetIndexRequest("aaa");
        GetIndexResponse response = indices.get(getRequest, RequestOptions.DEFAULT);
        Map<String, MappingMetaData> mappings = response.getMappings();
        System.out.println(mappings);
        System.out.println("-----------------------");
        for (String key : mappings.keySet()) {
            System.out.println(key + " : " + mappings.get(key).getSourceAsMap());
        }
    }
    
    @Test
    public void testDelIndex() throws IOException {
        IndicesClient indices = yxClient.indices();
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("aaa");
        AcknowledgedResponse response = indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test
    public void testExistIndex() throws IOException {
        IndicesClient indices = yxClient.indices();
        GetIndexRequest getIndexRequest = new GetIndexRequest("aaa");
        boolean exists = indices.exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    public void testAddDoc() throws IOException {
        Map data = new HashMap<>();
        data.put("address","深圳宝安");
        data.put("name","尚硅谷");
        data.put("age",20);

        IndexRequest request = new IndexRequest("aaa").id("2").source(data);
        IndexResponse response = yxClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getId());
    }

    @Test
    public void testAddBean() throws IOException {
        Person person = new Person();
        person.setId("3");
        person.setName("yxxx");
        person.setAddress("古城，阆中");
        person.setAge(30);

        String data = JSON.toJSONString(person);
        IndexRequest request = new IndexRequest("aaa").id(person.getId()).source(data, XContentType.JSON);
        IndexResponse res = yxClient.index(request, RequestOptions.DEFAULT);
        System.out.println(res.getId());
    }

    @Test
    public void testUpdateBean() throws IOException {
        Person person = new Person();
        person.setId("5");
        person.setName("yxxxyx");
        person.setAddress("古城");
        person.setAge(30);

        String data = JSON.toJSONString(person);
        IndexRequest request = new IndexRequest("aaa").id(person.getId()).source(data, XContentType.JSON);
        IndexResponse res = yxClient.index(request, RequestOptions.DEFAULT);
        System.out.println(res.getId());
    }

    @Test
    public void testFindById() throws IOException {
        GetRequest getRequest = new GetRequest("aaa", "1");
        GetResponse documentFields = yxClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields.getSourceAsString());
    }

    @Test
    public void testDelDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("aaa","1");
        DeleteResponse delete = yxClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    @Test
    public void testBulk() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        DeleteRequest deleteRequest = new DeleteRequest("aaa", "SUarw3sBJkcM1e-xTbz2");
        bulkRequest.add(deleteRequest);

        Map hashMap = new HashMap();
        hashMap.put("name","yoho");
        IndexRequest indexRequest = new IndexRequest("aaa").id("6").source(hashMap);
        bulkRequest.add(indexRequest);

        Map hashMap1 = new HashMap();
        hashMap1.put("name","yahoooo");
        UpdateRequest updateRequest = new UpdateRequest("aaa", "3").doc(hashMap1);
        bulkRequest.add(updateRequest);

        BulkResponse bulk = yxClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        RestStatus status = bulk.status();
        System.out.println(status);
    }

    @Test
    public void testImportData() throws IOException {
        List<Goods> goodsLists = goodsMapper.findAll();
        BulkRequest bulkRequest = new BulkRequest();
        for (Goods good : goodsLists) {
            String specStr = good.getSpecStr();
            Map map = JSON.parseObject(specStr, Map.class);
            good.setSpec(map);
            String data = JSON.toJSONString(good);
            IndexRequest indexRequest = new IndexRequest("goods");
            indexRequest.id(String.valueOf(good.getId())).source(data,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = yxClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.status());
    }
    
    @Test
    public void testMatchAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(queryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(100);

        SearchResponse response = yxClient.search(searchRequest, RequestOptions.DEFAULT);
        printGoods(response);
    }

    @Test
    public void testTermQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "华为");
        sourceBuilder.query(termQueryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse response = yxClient.search(searchRequest, RequestOptions.DEFAULT);
        printGoods(response);
    }

    @Test
    public void testMatchQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "华为手机");
        matchQueryBuilder.operator(Operator.AND);
        sourceBuilder.query(matchQueryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = yxClient.search(searchRequest, RequestOptions.DEFAULT);
        printGoods(searchResponse);
    }



    @Test
    public void testWildcardQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        WildcardQueryBuilder query = QueryBuilders.wildcardQuery("title", "华*");
        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = yxClient.search(searchRequest, RequestOptions.DEFAULT);
        printGoods(searchResponse);
    }

    @Test
    public void testPrefixQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        PrefixQueryBuilder query = QueryBuilders.prefixQuery("brandName", "三");
        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = yxClient.search(searchRequest, RequestOptions.DEFAULT);
        printGoods(searchResponse);
    }

    @Test
    public void testRangeQuery()throws IOException{
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder query = QueryBuilders.rangeQuery("price");
        query.gte(2000);
        query.lte(3000);
        sourceBuilder.query(query);
        sourceBuilder.sort("price", SortOrder.ASC);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = yxClient.search(searchRequest, RequestOptions.DEFAULT);
        printGoods(searchResponse);
    }

    @Test
    public void testQueryStringQuery()throws IOException{
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        QueryStringQueryBuilder query = QueryBuilders.queryStringQuery("华为手机")
                .field("title")
                .field("categoryName")
                .field("brandName")
                .defaultOperator(Operator.AND);
        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = yxClient.search(searchRequest, RequestOptions.DEFAULT);
        printGoods(searchResponse);
    }

    @Test
    public void testBoolQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        TermQueryBuilder termQuery = QueryBuilders.termQuery("brandName", "华为");
        boolQuery.must(termQuery);
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("title", "手机");
        boolQuery.filter(matchQuery);
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("price");
        rangeQuery.gte(2000);
        rangeQuery.lte(3000);
        boolQuery.filter(rangeQuery);
        sourceBuilder.query(boolQuery);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = yxClient.search(searchRequest, RequestOptions.DEFAULT);
        printGoods(searchResponse);
    }

    @Test
    public void testAggQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchSourceBuilder sourceBulider = new SearchSourceBuilder();
        MatchQueryBuilder query = QueryBuilders.matchQuery("title", "手机");
        sourceBulider.query(query);

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("goods_brands").field("brandName").size(100);
        sourceBulider.aggregation(aggregationBuilder);
        searchRequest.source(sourceBulider);

        SearchResponse searchResponse = yxClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();

        Map<String, Aggregation> stringAggregationMap = aggregations.asMap();

        Terms goodsBrands = (Terms) stringAggregationMap.get("goods_brands");

        List<? extends Terms.Bucket> buckets = goodsBrands.getBuckets();
        List brands = new ArrayList();
        for (Terms.Bucket bucket : buckets) {
            Object key = bucket.getKey();
            brands.add(key);
        }

        for (Object brand : brands) {
            System.out.println(brand);
        }
    }

    @Test
    public void testHighLightQuery()throws IOException{
        SearchRequest searchRequest = new SearchRequest("goods");

        SearchSourceBuilder sourceBulider = new SearchSourceBuilder();

        MatchQueryBuilder query = QueryBuilders.matchQuery("title", "手机");

        sourceBulider.query(query);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        sourceBulider.highlighter(highlightBuilder);

        TermsAggregationBuilder agg = AggregationBuilders.terms("goods_brands").field("brandName").size(100);
        sourceBulider.aggregation(agg);

        searchRequest.source(sourceBulider);

        SearchResponse searchResponse = yxClient.search(searchRequest, RequestOptions.DEFAULT);

        List<Goods> goodsList = new ArrayList<>();

        SearchHit[] hits = searchResponse.getHits().getHits();

        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();

            Goods goods = JSON.parseObject(sourceAsString, Goods.class);

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Text[] fragments = title.getFragments();

            goods.setTitle(fragments[0].toString());

            goodsList.add(goods);
        }

        for (Goods goods : goodsList) {
            System.out.println(goods);
        }
    }


    private void printGoods(SearchResponse searchResponse) {
        System.out.println(searchResponse.getHits().getTotalHits());
        SearchHit[] hits = searchResponse.getHits().getHits();
        List<Goods> lists = new ArrayList<>();
        for (SearchHit good : hits) {
            String sourceAsString = good.getSourceAsString();
            Goods goods = JSON.parseObject(sourceAsString, Goods.class);
            lists.add(goods);
        }
        for (Goods list : lists) {
            System.out.println(list);
        }
    }
}
