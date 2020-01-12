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

    private int getMatchPercentage(Project project, User user){
        int matchPercentage = 0;

        for (Skill skill : project.requiredSkills){
            if(user.hasSkill(skill)){
                matchPercentage = matchPercentage + (1 / user.skills.stream().filter(skill1 -> skill1.equals(skill)).findFirst().get().sPercentage) * 100 / project.requiredSkills.size();
            }
        }

        return matchPercentage;
    }

    public void addProjectToPool(Project project) {
        this.projects.add(project);
    }

    public List<Project> getMatchedProjects(User user) {
        return this.projects.stream().filter(project -> project.contributers.contains(user)).collect(Collectors.toList());
    }

    public Pair<Project, Integer> match(User user) {
        Pair<Project, Integer> bestProjectForUser = new Pair<>(new Project(), 0);
        for (Project project: this.projects) {
            int matchPercentage = getMatchPercentage(project, user);
            if(matchPercentage > bestProjectForUser.getValue()){
                bestProjectForUser = new Pair<>(project, matchPercentage);
            }
        }


        return bestProjectForUser;
    }
}
