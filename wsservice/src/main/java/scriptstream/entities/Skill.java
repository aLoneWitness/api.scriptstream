package scriptstream.entities;

public class Skill {
    public int id;
    public String name;
    public double sPercentage;

    @Override
    public boolean equals(Object obj) {
        Skill skill = (Skill) obj;
        return this.name.toLowerCase().equals(skill.name.toLowerCase());
    }
}
