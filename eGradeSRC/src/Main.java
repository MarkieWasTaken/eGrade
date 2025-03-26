import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        connectToDatabase();
        new LoginForm();
    }

    private static void connectToDatabase() {
        String url = "jdbc:postgresql://pg-28426f42-e-gradejava.c.aivencloud.com:24905/defaultdb?sslmode=require";
        String user = "avnadmin";
        String password = "AVNS_Zlomc5dK4BVOJnWDL66";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Database connected successfully!");
            connection.close();
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}
