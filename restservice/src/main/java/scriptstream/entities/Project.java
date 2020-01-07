package scriptstream.entities;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class Project {
    @Getter
    private User owner;
    @Getter
    private List<User> contributers;
    @Getter
    private List<Skill> requiredSkills;
    @Getter
    private UUID uuid = UUID.randomUUID();

    public Project(User owner){
        this.owner = owner;
    }
}
