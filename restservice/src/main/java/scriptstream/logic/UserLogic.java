package scriptstream.logic;

import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.logic.repositories.UserRepository;

import java.util.UUID;

public class UserLogic  {
    private UserRepository userRepository;

    public UserLogic(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUUID(UUID uuid){
        User user = new User();
        user.uuid = uuid;
        return this.userRepository.read(user);
    }

    public boolean addSkillToUser(User user, Skill skill){
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
        return true;
    }

    public boolean removeProjectFromUser(User user, Project project) {
        if(!user.ownedProjects.removeIf(project1 -> project1.equals(project))) return false;
        this.userRepository.update(user);
        return true;
    }

    public boolean leaveProjectFromUser(User user, Project project) {
        if(!user.joinedProjects.removeIf(project1 -> project1.equals(project))) return false;
        this.userRepository.update(user);
        return true;
    }


}
