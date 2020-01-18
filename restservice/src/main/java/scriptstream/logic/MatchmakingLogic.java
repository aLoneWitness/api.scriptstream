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

    public void addProjectToPool(Project project) {
        this.projects.add(project);
    }

    public Pair<Project, Double> match(User user) {
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
