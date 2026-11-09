package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/** TASK 01 - JDBC connection helper. Reads settings from db.properties. */
public final class DBUtil {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                PROPS.load(in);
            }
            Class.forName(PROPS.getProperty("db.driver", "com.mysql.cj.jdbc.Driver"));
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Could not initialise JDBC: " + e);
        }
    }

    private DBUtil() { }

    private static String setting(String envName, String propName) {
        String env = System.getenv(envName);
        return (env != null && !env.isEmpty()) ? env : PROPS.getProperty(propName);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                setting("MATHWHIZ_DB_URL", "db.url"),
                setting("MATHWHIZ_DB_USER", "db.user"),
                setting("MATHWHIZ_DB_PASSWORD", "Sh@oun21ict"));
    }
}
