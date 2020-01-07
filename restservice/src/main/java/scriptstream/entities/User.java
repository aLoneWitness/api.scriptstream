package scriptstream.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@DatabaseTable(tableName = "users")
public class User {
    @DatabaseField
    public int id;
    @Getter @DatabaseField
    public String name;
    @DatabaseField
    public String gToken;
    @DatabaseField
    public List<Skill> skills;

    public boolean hasSkill(Skill skill){
        for (Skill mySkill : this.skills){
            if(skill.equals(mySkill)){
                return true;
            }
        }
        return false;
    }
}
