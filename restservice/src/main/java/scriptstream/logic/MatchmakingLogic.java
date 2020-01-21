package scriptstream.logic;

import javafx.util.Pair;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Pair<Project, Double> match(User user) {
        if(user == null || user.skills.isEmpty() || user.name.isEmpty() || user.uuid == null) return null;

        Pair<Project, Double> bestProjectForUser = new Pair<Project, Double>(null, 0.0);
        for (Project project: this.projects) {
            double matchPercentage = getMatchPercentage(project, user);
            if(matchPercentage > bestProjectForUser.getValue()){
                bestProjectForUser = new Pair<>(project, matchPercentage);
            }
        }


        return bestProjectForUser;
    }
}
