package com.diplom.routeoptimizer.services.routerequester.gis2.impl;

import com.diplom.routeoptimizer.dto.RouteDetailsRequest;
import com.diplom.routeoptimizer.dto.RouteDetailsResponse;
import com.diplom.routeoptimizer.services.routerequester.gis2.GIS2Requester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class GIS2RequesterImpl implements GIS2Requester {

    private final RestTemplate restTemplate;
    private final String URL;
    private final String API_KEY;

    @Autowired
    public GIS2RequesterImpl(RestTemplate restTemplate,
                             @Value("${gis.url}") String URL,
                             @Value("${gis.api_key}") String API_KEY) {
        this.restTemplate = restTemplate;
        this.URL = URL;
        this.API_KEY = API_KEY;
    }

    @Override
    public ResponseEntity<RouteDetailsResponse> getRouteDetails(RouteDetailsRequest body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RouteDetailsRequest> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(String.format("%s?key=%s", URL, API_KEY), entity, RouteDetailsResponse.class);
    }
}
