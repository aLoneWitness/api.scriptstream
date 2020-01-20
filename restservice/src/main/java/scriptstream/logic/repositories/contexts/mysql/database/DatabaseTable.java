package scriptstream.logic.repositories.contexts.mysql.database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseTable {
    public String columnName();
}
