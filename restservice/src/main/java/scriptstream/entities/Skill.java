package scriptstream.entities;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class Skill {
    public int id;
    public String name;
    public int sPercentage;
}
