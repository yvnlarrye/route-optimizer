package com.diplom.routeoptimizer.services.graphhopper;

import com.diplom.routeoptimizer.config.GraphHopperConfig;
import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.model.MatrixType;
import com.diplom.routeoptimizer.requests.ParameterStringBuilder;
import com.diplom.routeoptimizer.requests.Requester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GraphHopperRequesterImpl implements GraphHopperRequester {

    private final GraphHopperConfig config;
    private final Requester requester;

    private JSONObject buildMatrixRequestBody(List<Location> locations, List<MatrixType> matrixTypes) {
        JSONObject requestBody = new JSONObject();

        JSONArray pointsArray = new JSONArray();
        for (Location location : locations) {
            JSONArray currentLocation = new JSONArray();
            currentLocation.put(location.getLongitude());
            currentLocation.put(location.getLatitude());
            pointsArray.put(currentLocation);
        }

        JSONArray matrixTypesArr = new JSONArray();
        for (MatrixType type : matrixTypes) {
            matrixTypesArr.put(type.getName());
        }

        requestBody.put("points", pointsArray);
        requestBody.put("out_arrays", matrixTypesArr);
        requestBody.put("vehicle", "car");
        return requestBody;
    }

    @Override
    public String getMatrices(List<Location> locations, List<MatrixType> matrixTypes)
                        throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("key", config.getSecret());
        String formattedParams = ParameterStringBuilder.getParamsString(params);

        JSONObject requestBody = buildMatrixRequestBody(locations, matrixTypes);

        String endpoint = config.getUrl() + "?" + formattedParams;

        HttpResponse<String> response = requester.doPost(endpoint, requestBody.toString());

        int statusCode;
        if ((statusCode = response.statusCode()) <= 299) {
            log.info(String.format("Matrices request was sent with status %d", statusCode));
        } else {
            log.error(String.format("Matrices request failed with status code %d", statusCode));
        }

        return response.body();
    }
}
