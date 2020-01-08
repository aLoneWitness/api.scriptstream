package scriptstream.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

public class Project {
    public UUID uuid;

    public User owner;
    public List<User> contributers;

    public List<Skill> requiredSkills;
}
