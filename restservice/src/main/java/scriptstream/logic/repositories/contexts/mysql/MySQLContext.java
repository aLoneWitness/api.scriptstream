package scriptstream.logic.repositories.contexts.mysql;

import scriptstream.logic.repositories.contexts.IContext;
import scriptstream.logic.repositories.contexts.mysql.database.Database;
import scriptstream.logic.repositories.contexts.mysql.database.DatabaseTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class MySQLContext<T> implements IContext<T> {
    Database database;

    public MySQLContext(String serverName, int portNumber, String databaseName, String user, String password){
        this.database = new Database(serverName, portNumber, databaseName, user, password);
    }

    @Override
    public void create(T entity) {
        List<String> placeholders = new ArrayList<String>();
        List<String> columns = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            try {
                columns.add(field.getAnnotation(DatabaseTable.class).columnName());
                field.setAccessible(true);
                values.add(field.get(entity));
                placeholders.add("?");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        int id = database.executeNonQuery("INSERT INTO " + entity.getClass().getSimpleName().toLowerCase() + " (" + String.join(", ", columns) + ") VALUES(" + String.join(", ", placeholders) +")", values);
        System.out.println(id);
    }
}
