package com.diplom.routeoptimizer.geocoding;

import com.diplom.routeoptimizer.model.MapPoint;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CoordinatesParser implements GeocodingParser {
    @Override
    public MapPoint parse(String json) {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        String lat = jsonObject.getString("lat");
        String lon = jsonObject.getString("lon");

        return new MapPoint(Double.parseDouble(lat), Double.parseDouble(lon));
    }
}
