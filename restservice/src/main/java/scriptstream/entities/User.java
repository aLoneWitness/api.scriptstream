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

    public List<UUID> ownedProjects = new ArrayList<>();
    public List<UUID> joinedProjects = new ArrayList<>();

    public List<Skill> skills = new ArrayList<Skill>();

    public boolean hasSkill(Skill skill){
        for (Skill mySkill : this.skills){
            if(mySkill.equals(skill)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        User usr = (User) obj;
        if(this.uuid == null || usr.uuid == null) return false;
        return usr.uuid.equals(this.uuid);
    }
}
