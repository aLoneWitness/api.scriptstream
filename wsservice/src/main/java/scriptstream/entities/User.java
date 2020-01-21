package scriptstream.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    public int id;
    public UUID uuid;
    public String name;
    public String gToken;

    public List<Project> ownedProjects = new ArrayList<>();
    public List<Project> joinedProjects = new ArrayList<>();
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
        return usr.uuid.equals(this.uuid);
    }
}
