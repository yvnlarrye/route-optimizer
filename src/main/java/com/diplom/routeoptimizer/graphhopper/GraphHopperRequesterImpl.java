package com.diplom.routeoptimizer.graphhopper;

import com.diplom.routeoptimizer.config.GraphHopperConfig;
import com.diplom.routeoptimizer.model.MapPoint;
import com.diplom.routeoptimizer.model.MatrixType;
import com.diplom.routeoptimizer.requests.ParameterStringBuilder;
import com.diplom.routeoptimizer.requests.Requester;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
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
public class GraphHopperRequesterImpl implements GraphHopperRequester {

    @Autowired
    private GraphHopperConfig config;

    @Autowired
    private Requester requester;

    private JSONObject buildMatrixRequestBody(List<MapPoint> points, List<MatrixType> matrixTypes) {
        JSONObject requestBody = new JSONObject();

        JSONArray pointsArray = new JSONArray();
        for (MapPoint point : points) {
            JSONArray currentPoint = new JSONArray();
            currentPoint.put(point.getLatitude());
            currentPoint.put(point.getLongitude());
            pointsArray.put(currentPoint);
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
    public String getMatrices(List<MapPoint> points, List<MatrixType> matrixTypes) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("key", config.getSecret());
        String formattedParams = ParameterStringBuilder.getParamsString(params);

        JSONObject requestBody = buildMatrixRequestBody(points, matrixTypes);

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
