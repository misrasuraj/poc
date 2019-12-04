package com.pb.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * Created by AB001MI on 10/27/2017.
 */
public class ElasticQueryManager
{

  public static void main(String[] args)
  {
    RestClient lowLevelRestClient = RestClient.builder(
            new HttpHost("localhost", 9200, "http"),
            new HttpHost("localhost", 9201, "http")).build();
    //org.apache.http.client.config.RequestConfig

    RestHighLevelClient client =
            new RestHighLevelClient(lowLevelRestClient);

    try
    {
      client.ping();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    SearchRequest searchRequest = new SearchRequest("people");
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
    searchSourceBuilder.size(10000);
    searchRequest.source(searchSourceBuilder);
    searchRequest.scroll(TimeValue.timeValueMinutes(1L));
    SearchResponse searchResponse = null;
    try
    {
      searchResponse = client.search(searchRequest);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    RestStatus status = searchResponse.status();
    TimeValue took = searchResponse.getTook();
    Boolean terminatedEarly = searchResponse.isTerminatedEarly();
    boolean timedOut = searchResponse.isTimedOut();
    int totalShards = searchResponse.getTotalShards();
    int successfulShards = searchResponse.getSuccessfulShards();
    int failedShards = searchResponse.getFailedShards();
    System.out.println("totalShards "+totalShards);
    System.out.println("successfulShards "+successfulShards);
    System.out.println("failedShards "+failedShards);

    SearchHits hits = searchResponse.getHits();
    long totalHits = hits.getTotalHits();
    SearchHit[] searchHits = hits.getHits();
    String scrollId = searchResponse.getScrollId();
    int count = 0;
    for (SearchHit hit : searchHits)
    {
      //System.out.println(hit.getSourceAsString());
      count++;
    }

    int numOfReleasedSearchContext = 0;
    while(numOfReleasedSearchContext ==0)
    {
      SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
      scrollRequest.scroll(TimeValue.timeValueSeconds(30));
      try
      {
        searchResponse = client.searchScroll(scrollRequest);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }

      hits = searchResponse.getHits();
      searchHits = hits.getHits();
      for (SearchHit hit : searchHits)
      {
        //System.out.println(hit.getSourceAsString());
        count++;
      }
      scrollId = searchResponse.getScrollId();
      if(totalHits==count)
      {
        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId(scrollId);
        try
        {
          ClearScrollResponse response = client.clearScroll(request);
          numOfReleasedSearchContext = response.getNumFreed();
          System.out.println(" response.getNumFreed() "+numOfReleasedSearchContext);
          break;
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
    System.out.println(count);
    System.out.println("Finished");
    try
    {
      lowLevelRestClient.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
