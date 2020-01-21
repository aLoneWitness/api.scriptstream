package scriptstream.logic;

import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.logic.repositories.IRepository;
import scriptstream.logic.repositories.ProjectRepository;
import scriptstream.logic.repositories.UserRepository;

import java.util.UUID;

public class UserLogic  {
    private IRepository<User> userRepository;
    private IRepository<Project> projectRepository;

    public UserLogic(IRepository<User> userRepository, IRepository<Project> projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public User getUserByUUID(UUID uuid){
        User user = new User();
        user.uuid = uuid;
        return this.userRepository.read(user);
    }

    public boolean addSkillToUser(User user, Skill skill){
        if(skill.sPercentage > 100 || skill.sPercentage < 0) return false;
        if(skill.name.isEmpty()) return false;
        user.skills.add(skill);
        this.userRepository.update(user);
        return true;
    }

    public boolean removeSkillFromUser(User user, Skill skill) {
        if(!user.skills.removeIf(skill1 -> skill1.equals(skill))) return false;
        this.userRepository.update(user);
        return true;
    }

    public boolean addProjectToUser(User user, Project project) {
        user.joinedProjects.add(project);
        this.userRepository.update(user);
        return true;
    }

    public boolean addNewProjectToUser(User user, Project project) {
        user.ownedProjects.add(project);
        this.userRepository.update(user);
        this.projectRepository.create(project);
        return true;
    }

    public boolean removeProjectFromUser(User user, Project project) {
        if(!user.ownedProjects.removeIf(project1 -> project1.equals(project))) return false;
        this.userRepository.update(user);
        this.projectRepository.delete(project);
        return true;
    }

    public boolean leaveProjectFromUser(User user, Project project) {
        if(!user.joinedProjects.removeIf(project1 -> project1.equals(project))) return false;
        this.userRepository.update(user);
        return true;
    }
}
