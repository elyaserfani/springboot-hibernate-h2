package com.simplespringboot.app.elastic;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
public class ElasticSearchClient {

    private RestHighLevelClient client;

    @PostConstruct
    public void init() {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "https")));
    }

    @PreDestroy
    public void close() throws IOException {
        client.close();
    }

    public RestHighLevelClient getClient() {
        return client;
    }
}