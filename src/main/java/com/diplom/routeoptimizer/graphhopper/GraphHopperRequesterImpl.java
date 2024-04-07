//package com.diplom.routeoptimizer.graphhopper;
//
//import com.diplom.routeoptimizer.config.GraphHopperConfig;
//import com.diplom.routeoptimizer.model.MapPoint;
//import com.diplom.routeoptimizer.model.MatrixType;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.*;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class GraphHopperRequesterImpl implements GraphHopperRequester {
//
//    @Autowired
//    private GraphHopperConfig config;
//
//    @Override
//    public double[][] getMatrices(List<MapPoint> points, List<MatrixType> matrixTypes) throws IOException {
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(config.getUrl()).newBuilder();
//
//        urlBuilder.addQueryParameter("key", config.getSecret());
//
//        String url = urlBuilder.build().toString();
//
//        OkHttpClient client = new OkHttpClient();
//
//
//        String json = "{\"points\":[[-0.11379003524780275, 51.53664617804063], [-0.10866165161132814, 51.538621486960956], [-0.11059284210205078, 51.53245503603458]],\"out_arrays\":[\"distances\", \"times\"]}";
//        RequestBody body = RequestBody.create(
//                MediaType.parse("application/json"), json);
//
//
//
//        System.out.println(new JSONArray(matrixTypes));
//
//        JSONArray pointsArray = new JSONArray();
//        for (MapPoint point : points) {
//            JSONArray currentPoint = new JSONArray();
//            currentPoint.put(point.getLongitude());
//            currentPoint.put(point.getLatitude());
//            pointsArray.put(currentPoint);
//        }
//
//        JSONObject object = new JSONObject();
//        object.append("points", pointsArray);
//
//        RequestBody body = new FormBody.Builder()
//                .add("points", new JSONArray(points).toString())
//                .add("points", "[[-0.11379003524780275, 51.53664617804063], [-0.10866165161132814, 51.538621486960956], [-0.11059284210205078, 51.53245503603458]]")
//                .add("out_arrays", new JSONArray(
//                        matrixTypes.stream().map(MatrixType::getName).
//                                collect(Collectors.toList())).toString())\
//                .add("out_arrays", "[\"distances\", \"times\"]")
//                .add("vehicle", "car")
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        Call call = client.newCall(request);
//
//        Response response = call.execute();
//        JSONObject jsonObject = new JSONObject(response.body().string());
//
//        log.info(String.format("Matrix request was sent with status %d", response.code()));
//
//        return jsonObject;
//    }
//}
