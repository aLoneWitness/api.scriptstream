package scriptstream.logic;

import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.logic.repositories.IRepository;

import java.util.UUID;

public class ProjectLogic {
    private IRepository<Project> projectRepository;

    public ProjectLogic(IRepository<Project> projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project getProjectByUUID(UUID uuid){
        Project project = new Project();
        project.uuid = uuid;
        return projectRepository.read(project);
    }

    public boolean addSkillToProject(Project project, Skill skill) {
        if(skill.sPercentage > 100 || skill.sPercentage < 0) return false;
        if(skill.name.isEmpty()) return false;
        project.requiredSkills.add(skill);
        projectRepository.update(project);
        return true;
    }

    public boolean removeSkillFromProject(Project project, Skill skill) {
        if(project.requiredSkills.contains(skill)){
            project.requiredSkills.remove(skill);
            projectRepository.update(project);
            return true;
        }
        return false;
    }
}
