package com.diplom.routeoptimizer.services.geocode.parser.impl;

import com.diplom.routeoptimizer.services.geocode.Location;
import com.diplom.routeoptimizer.services.geocode.parser.LocationParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component("gis2LocationParser")
public class GISLocationParser implements LocationParser {
    @Override
    public Location parse(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject result = (JSONObject) jsonObject.get("result");
        JSONArray items = (JSONArray) result.get("items");
        JSONObject arrayFirstObject = (JSONObject) items.get(0);
        JSONObject point = (JSONObject) arrayFirstObject.get("point");

        return new Location(point.getDouble("lat"), point.getDouble("lon"));
    }
}
