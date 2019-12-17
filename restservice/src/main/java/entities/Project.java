package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Entity @Getter @Setter
public class Project {
    private User owner;
    private List<User> contributers;
    private List<Skill> requiredSkills;
    private UUID uuid = UUID.randomUUID();
}
