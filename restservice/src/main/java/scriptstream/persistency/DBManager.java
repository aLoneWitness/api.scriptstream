package scriptstream.persistency;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;

import java.sql.SQLException;

public class DBManager {
    private ConnectionSource connectionSource;
    public DBManager() {
        try {
            this.connectionSource = new JdbcConnectionSource("localhost:3306");

            Dao<User, Integer> userDao = DaoManager.createDao(connectionSource, User.class);
            Dao<Project, Integer> projectDao = DaoManager.createDao(connectionSource, Project.class);
            Dao<Skill, Integer> skillDao = DaoManager.createDao(connectionSource, Skill.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
