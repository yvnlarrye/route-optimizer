package com.diplom.routeoptimizer.requests;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CustomHttpClient implements HttpClient {

    @Override
    public HttpResponse<String> doGet(String url) throws IOException, InterruptedException {
        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public HttpResponse<String> doPost(String url, String requestBody) throws IOException, InterruptedException {
        java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
        RestTemplate restTemplate = new RestTemplate();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
