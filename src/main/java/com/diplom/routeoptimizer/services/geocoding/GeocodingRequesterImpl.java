package com.diplom.routeoptimizer.services.geocoding;

import com.diplom.routeoptimizer.config.GeocodingConfig;
import com.diplom.routeoptimizer.model.UniversalAddress;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.requests.Requester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@RequiredArgsConstructor
public class GeocodingRequesterImpl implements GeocodingRequester {

    private final GeocodingConfig config;
    private final GeocodingParser parser;
    private final Requester requester;

    @Override
    public Location getLocationFromAddress(UniversalAddress address) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", config.getSecret());
        params.put("street",
                String.format("%s, %s", address.getStreet(), address.getHouseNumber()));
        params.put("city", address.getCity());
        params.put("country", address.getCountry());

        HttpResponse<String> response = requester.doGet(config.getUrl(), params);

        int statusCode;
        if ((statusCode = response.statusCode()) <= 299) {
            log.info(String.format("Geocoding request was sent with status %d", statusCode));
        } else {
            log.error(String.format("Geocoding request was sent, but server responded with status code %d", statusCode));
        }

        return parser.parse(response.body());
    }
}
