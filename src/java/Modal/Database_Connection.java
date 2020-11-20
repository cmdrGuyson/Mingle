package Modal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database_Connection {

    
    /* To connect to the database */
    public static Connection connectToDatabase() {

        /*Credentials*/
        String URL = "jdbc:mysql://localhost:3306/mingle";
        String username = "root";
        String password = "";

        try {

            Class.forName("com.mysql.jdbc.Driver");
            // New connection is made to the database.
            Connection connection = DriverManager.getConnection(URL, username, password);
            
            // The connection is returned
            return connection;

        } catch (ClassNotFoundException | SQLException e) {

            // Any errors are caught and displayed
            System.out.println(e);
            return null;
        }
    }

    /* To disconnect from the database */
    public static void disconnectFromDatabase(Connection connection) {

        try {

            // Connection is closed
            connection.close();

        } catch (SQLException e) {

            System.out.println(e);
        }
    }
}
