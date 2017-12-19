package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    public static boolean getAccount(String login, String passw) throws SQLException {
        int counter = 0;
        Connection conn = null;
        ResultSet res = null;
        try {
            conn = ConnectionConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT idPracownik FROM kontaSystemowe WHERE login = '" + login + "' AND haslo = '" + passw + "'");
            res = statement.executeQuery();

            while(res.next()){
                counter++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(counter == 1)
            return true;
        return false;
    }
}
