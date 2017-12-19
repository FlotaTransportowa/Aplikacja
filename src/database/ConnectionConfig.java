package database;

import org.json.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfig {
    public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }

    public static Connection getConnection() throws Exception{
        Connection connection = null;
        String filename = "src/resources/databaseInfo.json";
        JSONObject jsonObject = parseJSONFile(filename);
        JSONArray jsonArray;
        jsonArray = jsonObject.getJSONArray("mainConnectionString");

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jsonArray.getJSONObject(0).getString("url"), jsonArray.getJSONObject(0).getString("user"),jsonArray.getJSONObject(0).getString("pass"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void connectDB(){
        Connection connection = null;

        try{
            connection = ConnectionConfig.getConnection();
            if(connection != null){
                System.out.println("Połączono z bazą!");
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
