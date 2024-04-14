package com.diplom.routeoptimizer.graphhopper;

import com.diplom.routeoptimizer.model.MatrixType;
import org.json.JSONArray;
import org.json.JSONObject;


public class MatrixParser {

    public static Object[][] parseMatrix(String json, MatrixType type) {
        JSONObject response = new JSONObject(json);
        JSONArray rows = response.getJSONArray(type.getName());

        Object[][] result = new Object[rows.length()][rows.length()];

        for (int i = 0; i < rows.length(); i++) {
            JSONArray currRow = (JSONArray) rows.get(i);
            for (int j = 0; j < currRow.length(); j++) {
                var matrixElement = currRow.get(j);
                result[i][j] = matrixElement;
            }
        }

        return result;
    }

}
