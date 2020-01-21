package scriptstream.logic;

import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;

import java.util.*;
import java.util.stream.Collectors;

public class MatchmakingLogic {
    private List<Project> projects = new ArrayList<>();

    private double getMatchPercentage(Project project, User user){
        double matchPercentage = 0;

        for (Skill requiredSkill : project.requiredSkills){
            if(!user.hasSkill(requiredSkill)) continue;
            for (Skill userSkill: user.skills) {
                if(userSkill.equals(requiredSkill)){
                    matchPercentage += userSkill.sPercentage / project.requiredSkills.size();
                }
            }
        }
        return matchPercentage;
    }

    public boolean addProjectToPool(Project project) {
        if(project == null || project.uuid == null || project.requiredSkills.isEmpty() || project.name.isEmpty()) return false;
        this.projects.add(project);
        return true;
    }

    public boolean removeProjectFromPool(Project project) {
        if(this.projects.stream().anyMatch(project1 -> project1.equals(project))){
            Project actual = this.projects.stream().filter(project1 -> project1.equals(project)).findFirst().get();
            return(this.projects.remove(actual));
        }
        return false;
    }

    public AbstractMap.SimpleEntry<Project, Double> match(User user) {
        if(user == null || user.skills.isEmpty() || user.name.isEmpty() || user.uuid == null) return null;

        AbstractMap.SimpleEntry<Project, Double> bestProjectForUser = new AbstractMap.SimpleEntry<>(null, 0.0);
        for (Project project: this.projects) {
            double matchPercentage = getMatchPercentage(project, user);
            if(matchPercentage > bestProjectForUser.getValue()){
                bestProjectForUser = new AbstractMap.SimpleEntry<>(project, matchPercentage);
            }
        }


        return bestProjectForUser;
    }
}
