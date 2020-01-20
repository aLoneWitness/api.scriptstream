package scriptstream.logic.repositories.contexts.mysql.database;

import javafx.util.Pair;
import org.mariadb.jdbc.MariaDbDataSource;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    MariaDbDataSource dataSource;

    public Database(String serverName, int portNumber, String databaseName, String user, String password){
        try {
            setConnection(serverName, portNumber, databaseName, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setConnection(String serverName, int portNumber, String databaseName, String user, String password) throws SQLException {
        dataSource = new MariaDbDataSource();
        dataSource.setServerName(serverName);
        dataSource.setPortNumber(portNumber);
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(user);
        dataSource.setPassword(password);
    }

    public ResultSet executeQuery(String query, List<Object> parameters){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int i = 0;
            for (Object parameter : parameters){
                i++;
                preparedStatement.setObject(i, parameter);
            }
            return preparedStatement.executeQuery();
        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public int executeNonQuery(String query, List<Object> parameters){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int i = 0;
            for (Object parameter : parameters){
                i++;
                preparedStatement.setObject(i, parameter);
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating entity failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return (int) generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating entity failed, no ID obtained.");
                }
            }
        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    public boolean exists(String query, Object parameter){
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("rowcount");
            return count != 0;
        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return true;
    }

}
