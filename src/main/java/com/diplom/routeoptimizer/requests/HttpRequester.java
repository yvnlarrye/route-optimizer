package com.diplom.routeoptimizer.requests;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Component
public class HttpRequester implements Requester {

    @Override
    public HttpResponse<String> doGet(String url,
                        Map<String, String> queryParams) throws IOException, InterruptedException {
        String formattedParams = ParameterStringBuilder.getParamsString(queryParams);
        return doGet(url + "?" + formattedParams);
    }

    @Override
    public HttpResponse<String> doGet(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET().build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public HttpResponse<String> doPost(String url, Map<String, Object> requestBody) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString("")).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
