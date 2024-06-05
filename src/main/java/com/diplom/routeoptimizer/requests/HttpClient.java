package com.diplom.routeoptimizer.requests;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

public interface HttpClient {
    HttpResponse<String> doGet(String url) throws IOException, InterruptedException;

    HttpResponse<String> doPost(String url, String requestBody)
            throws IOException, InterruptedException;

    default HttpResponse<String> doGet(String url, Map<String, String> queryParams)
            throws IOException, InterruptedException {
        return doGet(url + "?" + ParameterStringBuilder.getParamsString(queryParams));
    }

}
