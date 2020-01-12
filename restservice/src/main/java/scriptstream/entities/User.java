package scriptstream.entities;



import java.util.List;
import java.util.UUID;

public class User {
    public UUID uuid;
    public String name;
    public String gToken;

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
