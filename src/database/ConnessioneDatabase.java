package database;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnessioneDatabase {

    private static ConnessioneDatabase instance;
    public Connection connection = null;
    private String user;
    private String password;
    private String url;
    private String driver;
    private ConnessioneDatabase() throws SQLException {
        try {

            InputStream databasePropertiesFile = ConnessioneDatabase.class.getClassLoader().getResourceAsStream("database.properties");

            Properties databaseProperties = new Properties();
            databaseProperties.load(databasePropertiesFile);
            url = databaseProperties.getProperty("url");
            user = databaseProperties.getProperty("user");
            password = databaseProperties.getProperty("password");
            driver = databaseProperties.getProperty("driver");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDatabase();
        } else if (instance.connection.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

}
