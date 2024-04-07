package com.diplom.routeoptimizer.requests;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

public interface Requester {
    HttpResponse<String> doGet(String url) throws IOException, InterruptedException;
    HttpResponse<String> doGet(String url, Map<String, String> queryParams) throws IOException, InterruptedException;
    HttpResponse<String> doPost(String url, Map<String, Object> requestBody) throws IOException, InterruptedException;
}
