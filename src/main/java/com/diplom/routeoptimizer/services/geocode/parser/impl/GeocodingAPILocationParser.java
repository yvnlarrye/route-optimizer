package com.diplom.routeoptimizer.services.geocode.parser.impl;

import com.diplom.routeoptimizer.services.geocode.Location;
import com.diplom.routeoptimizer.services.geocode.parser.LocationParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component("geocodingAPIParser")
public class GeocodingAPILocationParser implements LocationParser {
    @Override
    public Location parse(String json) {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        String lat = jsonObject.getString("lat");
        String lon = jsonObject.getString("lon");

        return new Location(Double.parseDouble(lat), Double.parseDouble(lon));
    }
}
