package scriptstream.logic;

import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.repositories.UserRepository;

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
        user.skills.removeIf(skill1 -> skill1.equals(skill));
        this.userRepository.update(user);
        return true;
    }

}
