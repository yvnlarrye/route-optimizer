package com.diplom.routeoptimizer.services.geocoding;

import com.diplom.routeoptimizer.config.GeocodingConfig;
import com.diplom.routeoptimizer.model.UniversalAddress;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.requests.Requester;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class GeocodingRequesterImpl implements GeocodingRequester {

    @Autowired
    private GeocodingConfig config;

    @Autowired
    private GeocodingParser parser;

    @Autowired
    private Requester requester;

    @Override
    public String encodeAddressToCoordinates(Addressable address) throws IOException, InterruptedException {
        UniversalAddress universalAddress = (UniversalAddress) address;

        Map<String, String> params = new HashMap<>();
        params.put("api_key", config.getSecret());
        params.put("street",
                String.format("%s, %s", universalAddress.getStreet(), universalAddress.getHouseNumber()));
        params.put("city", universalAddress.getCity());
        params.put("country", universalAddress.getCountry());

        JSONObject resultJSON = new JSONObject();
        HttpResponse<String> response = requester.doGet(config.getUrl(), params);
        int statusCode;
        if ((statusCode = response.statusCode()) <= 299) {
            log.info(String.format("Geocoding request was sent with status %d", statusCode));
        } else {
            log.error(String.format("Geocoding request was sent, but server responded with status code %d", statusCode));
        }

        Location point = parser.parse(response.body());

        resultJSON.put("status", "ok");
        resultJSON.put("lat", point.getLatitude());
        resultJSON.put("lot", point.getLongitude());

        return resultJSON.toString();
    }
}
