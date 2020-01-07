package scriptstream.logic;

import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Matchmaker {
    private static List<Project> projects = new ArrayList<>();

    public static void addPublicProject(Project project){
        projects.add(project);
    }

    public static int getMatchPercentage(Project project, User user){
        int matchPercentage = 0;

        for (Skill skill : project.requiredSkills){
            if(user.hasSkill(skill)){
                matchPercentage = matchPercentage + (1 / user.skills.stream().filter(skill1 -> skill1.equals(skill)).findFirst().get().sPercentage) * 100 / project.requiredSkills.size();
            }
        }

        return matchPercentage;
    }
}
