package scriptstream.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;

@DatabaseTable(tableName = "skills")
public class Skill {
    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public String name;

    @Getter @DatabaseField
    public int sPercentage;
}
