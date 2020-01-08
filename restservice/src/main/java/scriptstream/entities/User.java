package scriptstream.entities;


import net.sf.jeasyorm.annotation.Column;
import net.sf.jeasyorm.annotation.Table;

import java.util.List;
import java.util.UUID;

@Table(schema = "scriptstream", name = "user")
public class User {
    @Column(name = "UUID")
    public UUID uuid;
    @Column(name = "name")
    public String name;
    @Column(name = "gToken")
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
