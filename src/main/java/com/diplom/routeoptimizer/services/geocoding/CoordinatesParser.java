package com.diplom.routeoptimizer.services.geocoding;

import com.diplom.routeoptimizer.model.Location;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CoordinatesParser implements GeocodingParser {
    @Override
    public Location parse(String json) {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        String lat = jsonObject.getString("lat");
        String lon = jsonObject.getString("lon");

        return new Location(Double.parseDouble(lat), Double.parseDouble(lon));
    }
}
