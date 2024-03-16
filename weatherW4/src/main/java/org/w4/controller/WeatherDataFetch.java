package org.w4.controller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class WeatherDataFetch {
    private static final String dbURL="jdbc:postgresql://localhost:5432/finalW4";
    private static final String USER="postgres";
    private static final String password="root";

    public static JSONArray getWeatherDataAsJson() throws SQLException {
        JSONArray recordList=new JSONArray();


        Connection connectionDB= DriverManager.getConnection(dbURL, USER, password);
        String sqlQuerySelection="select * from weather";
        PreparedStatement statement= connectionDB.prepareStatement(sqlQuerySelection);
        ResultSet result = statement.executeQuery();

        while (result.next()){
            JSONObject record=new JSONObject();
            record.put("city", result.getString("city"));
            record.put("temperature", result.getString("temperature"));
            record.put("pressure", result.getString("pressure"));

            recordList.put(record);
        }
        connectionDB.close();
        return recordList;
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(getWeatherDataAsJson().toString());
    }
}
