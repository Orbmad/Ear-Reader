package earreader.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/* DAO = Data Access Object Pattern */
public final class DAOUtils {

    // Establishes a connection to a MySQL daemon running locally at port 3306.
    public static Connection MySQLConnection(final String database, final String username, final String password) {
        try {
            var host = "localhost";
            var port = "3306";
            var connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;
            return DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /*
     * Prepares the a statement with the query string and insert the values in it.
     */
    public static PreparedStatement prepare(final Connection connection, final String query, final Object... values)
            throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement;
        } catch (Exception e) {
            if (statement != null) {
                statement.close();
            }
            throw e;
        }
    }
}
