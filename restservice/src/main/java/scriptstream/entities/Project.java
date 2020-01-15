package scriptstream.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {
    public UUID uuid;

    public User owner;
    public List<User> contributers = new ArrayList<>();

    public List<Skill> requiredSkills = new ArrayList<>();
}
