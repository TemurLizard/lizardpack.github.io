package org.w4;

import  org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataToDatabase {
    private static final String API_KEY="e4b3063181d69feced8cbb4f5de939d4";
    private static final String urlDB="jdbc:postgresql://localhost:5432/finalW4";
    private static final String USER="postgres";
    private static final String password="root";


    private static JSONObject getWeatherDataFromAPI(String city) throws IOException {
        String APIurl="http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;
        URL url=new URL(APIurl);
        HttpURLConnection connectionOpenWeather= (HttpURLConnection) url.openConnection();
        connectionOpenWeather.setRequestMethod("GET");

        BufferedReader reader=new BufferedReader(new InputStreamReader(connectionOpenWeather.getInputStream()));
        StringBuilder responce=new StringBuilder();
        String field=reader.readLine();// 1st line will be read

        while (field!=null){
            responce.append(field);
            field=reader.readLine();// next line will be read
        }
        reader.close();
        return new JSONObject(responce.toString());
    }
    private static void objectSavingIntoDB(JSONObject object) throws SQLException {
        Connection connectionDB= DriverManager.getConnection(urlDB, USER, password);
        String sqlQuery="insert into weather(city, temperature, pressure) values (?, ?, ?)";
        PreparedStatement statement=connectionDB.prepareStatement(sqlQuery);
//        statement.setString(3, object.getJSONArray("weather")
        statement.setString(1, object.getString("name"));
        statement.setDouble(2, object.getJSONObject("main").getDouble("temp"));
        statement.setDouble(3, object.getJSONObject("main").getDouble("pressure"));
        statement.execute();
        connectionDB.close();
    }

    public static void main(String[] args) throws IOException, SQLException {
        //System.out.println(getWeatherDataFromAPI("Toshkent"));
        String city="Sheffield";
        JSONObject forecast=getWeatherDataFromAPI(city);
        objectSavingIntoDB(forecast);
    }
}