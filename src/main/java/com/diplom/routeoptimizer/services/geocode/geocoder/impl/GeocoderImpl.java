package com.diplom.routeoptimizer.services.geocode.geocoder.impl;

import com.diplom.routeoptimizer.services.geocode.Location;
import com.diplom.routeoptimizer.requests.HttpClient;
import com.diplom.routeoptimizer.services.geocode.address.Addressable;
import com.diplom.routeoptimizer.services.geocode.parser.LocationParser;
import com.diplom.routeoptimizer.services.geocode.geocoder.Geocoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class GeocoderImpl implements Geocoder {

    private final String gis2ApiKey;
    private final String geocodingURL;
    private final LocationParser parser;
    private final HttpClient httpClient;


    @Autowired
    public GeocoderImpl(HttpClient httpClient,
                        @Qualifier("gis2LocationParser") LocationParser parser,
                        @Value("${gis.url.geocoding}") String geocodingURL,
                        @Value("${gis.api_key}") String gis2ApiKey) {
        this.httpClient = httpClient;
        this.parser = parser;
        this.geocodingURL = geocodingURL;
        this.gis2ApiKey = gis2ApiKey;
    }

    @Override
    public Location geocode(Addressable address) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("key", gis2ApiKey);
        params.put("fields", "items.point");
        params.put("q", address.oneStringAddress());

        HttpResponse<String> response = httpClient.doGet(geocodingURL, params);

        int statusCode;
        if ((statusCode = response.statusCode()) <= 299) {
            log.info(String.format("Geocoding request was sent with status %d", statusCode));
        } else {
            log.error(String.format("Geocoding request was sent, but server responded with status code %d", statusCode));
        }

        return parser.parse(response.body());
    }
}
