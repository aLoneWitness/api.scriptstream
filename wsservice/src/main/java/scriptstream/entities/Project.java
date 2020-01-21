package scriptstream.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {
    public String name;
    public UUID uuid;
    public List<Skill> requiredSkills = new ArrayList<>();
    public String code;
    public boolean isPublic;

    @Override
    public boolean equals(Object obj) {
        Project proj = (Project) obj;
        if(proj.uuid == null) return false;
        return proj.uuid.equals(this.uuid);
    }
}
