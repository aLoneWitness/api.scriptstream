package scriptstream.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@DatabaseTable(tableName = "projects")
public class Project {
    @DatabaseField
    public User owner;
    @DatabaseField
    public List<User> contributers;
    @DatabaseField
    public List<Skill> requiredSkills;
    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public UUID uuid = UUID.randomUUID();

    public Project(User owner){
        this.owner = owner;
    }
}
