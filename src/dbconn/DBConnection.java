package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection createConnection() {
        String url = "jdbc:mysql://localhost:3306/video_game_store";
        String username = "root";
        String password = "root";
        Connection dbConn = null;
        try {
            try {
                //loading MySQL drivers
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

            // Connect to DB
            dbConn = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbConn;
    }
}