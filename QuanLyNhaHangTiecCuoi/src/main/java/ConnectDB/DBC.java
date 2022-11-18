package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBC {
    private Connection conn;

    public DBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DB_URL = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
            String USER_NAME = "root";
            String PASSWORD = "281001";
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (Exception ex) {
            System.out.println("Connect failure!");
            ex.printStackTrace();
        }

    }
    public Connection getConnection() {
        return this.conn;
    }
    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
