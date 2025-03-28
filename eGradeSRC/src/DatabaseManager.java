import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://pg-28426f42-e-gradejava.c.aivencloud.com:24905/defaultdb?sslmode=require";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_Zlomc5dK4BVOJnWDL66";
    private static Connection connection;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    // Stores the last logged-in user ID
    public static int loggedInUserId = -1;

    public static String validateLogin(String email, String password) {
        String query = """
            SELECT u.id, ut.type_name
            FROM "user" u
            JOIN user_type ut ON u.user_type_id = ut.id
            WHERE u.email = ? AND u.password = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                loggedInUserId = rs.getInt("id");
                return rs.getString("type_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
