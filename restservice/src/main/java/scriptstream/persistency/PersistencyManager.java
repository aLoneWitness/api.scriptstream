package scriptstream.persistency;


import net.sf.jeasyorm.EntityManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PersistencyManager {
    private static Connection conn;
    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "secret");
            entityManager = EntityManager.getInstance(conn);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
