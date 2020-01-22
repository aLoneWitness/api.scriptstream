package scriptstream.logic;

import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.logic.repositories.IRepository;

import java.util.UUID;

public class ProjectLogic {
    private IRepository<User> userRepository;
    private IRepository<Project> projectRepository;

    public ProjectLogic(IRepository<Project> projectRepository, IRepository<User> userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project getProjectByUUID(UUID uuid){
        Project project = new Project();
        project.uuid = uuid;
        return projectRepository.read(project);
    }

    public boolean addSkillToProject(Project project, Skill skill) {
        if(skill.sPercentage > 100 || skill.sPercentage < 0) return false;
        if(skill.name.isEmpty()) return false;
        if(project.requiredSkills.contains(skill)) return false;
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

    public boolean disbandProject(Project project) {
        if(project.uuid == null || project.name.isEmpty()) return false;
        project.owner.ownedProjects.remove(project.uuid);
        for (User user: project.contributors) {
            removeContributor(project, user);
        }

        userRepository.update(project.owner);
        projectRepository.delete(project);
        return true;
    }

    public boolean addContributor(Project project, User user) {
        if(!project.isPublic) return false;
        if(user.uuid == null || user.name.isEmpty()) return false;
        if(project.contributors.contains(user)) return false;
        if(project.contributors.size() > 6) return false;

        project.contributors.add(user);
        user.joinedProjects.add(project.uuid);

        userRepository.update(user);
        projectRepository.update(project);
        return true;
    }

    public boolean removeContributor(Project project, User user) {
        if(user.uuid == null || user.name.isEmpty()) return false;
        if (!project.contributors.contains(user)) return false;

        project.contributors.remove(user);

        user.joinedProjects.remove(project.uuid);

        userRepository.update(user);
        projectRepository.update(project);
        return true;
    }

    public boolean createNewProject(Project project, User owner) {
        if(owner.uuid == null) return false;
        if(project.name.isEmpty()) return false;
        project.owner = owner;
        project.uuid = UUID.randomUUID();

        owner.ownedProjects.add(project.uuid);
        userRepository.update(owner);

        projectRepository.create(project);
        return true;
    }

    public boolean togglePrivacy(Project project) {
        if(project.name.isEmpty() || project.uuid == null) return false;
        project.isPublic = !project.isPublic;

        projectRepository.update(project);
        return true;
    }
}
