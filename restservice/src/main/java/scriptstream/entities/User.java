package scriptstream.entities;



import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    public int id;
    public UUID uuid;
    public String name;
    public String gToken;

    public List<Skill> skills = new ArrayList<Skill>();

    public boolean hasSkill(Skill skill){
        for (Skill mySkill : this.skills){
            if(skill.equals(mySkill)){
                return true;
            }
        }
        return false;
    }
}
