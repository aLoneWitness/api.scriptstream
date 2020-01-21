package scriptstream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.logic.UserLogic;
import scriptstream.logic.repositories.ProjectRepository;
import scriptstream.logic.repositories.UserRepository;
import scriptstream.logic.repositories.contexts.IContext;
import scriptstream.logic.repositories.contexts.memory.ProjectMemoryContext;
import scriptstream.logic.repositories.contexts.memory.UserMemoryContext;
import static org.junit.jupiter.api.Assertions.*;

public class UserLogicTests {
    private static UserLogic userLogic;

    @BeforeAll
    static public void beforeEach() {
        userLogic = new UserLogic(new UserRepository(new UserMemoryContext()));
    }

    @Test
    public void testAddSkillToUserWithValidSkill() {
        User user = new User();
        Skill skill = new Skill();
        skill.name = "Python";
        skill.sPercentage = 30;

        boolean succeeded = userLogic.addSkillToUser(user, skill);

        assertTrue(succeeded);
    }

    @Test
    public void testAddSkillToUserWithInvalidPercentage() {
        User user = new User();
        Skill skill = new Skill();
        skill.name = "Python";
        skill.sPercentage = 110;

        boolean succeeded = userLogic.addSkillToUser(user, skill);

        assertFalse(succeeded);
    }

    @Test
    public void testRemoveSkillFromUser() {
        User user = new User();
        Skill skill = new Skill();
        skill.name = "Python";
        skill.sPercentage = 50;
        userLogic.addSkillToUser(user, skill);

        boolean succeed = userLogic.removeSkillFromUser(user, skill);

        assertTrue(succeed);
    }

    @Test
    public void testRemoveSkillFromUserWhoDoesntHaveSaidSkill() {
        User user = new User();
        Skill skill = new Skill();
        skill.name = "Python";
        skill.sPercentage = 50;

        boolean succeed = userLogic.removeSkillFromUser(user, skill);

        assertFalse(succeed);
    }

    @Test
    public void testAddSkillToUserWithInvalidName() {
        User user = new User();
        Skill skill = new Skill();
        skill.name = "";
        skill.sPercentage = 60;

        boolean succeeded = userLogic.addSkillToUser(user, skill);

        assertFalse(succeeded);
    }

//    @Test
//    public void testRemoveProjectWhenTheUserDoesntOwnIt(){
//        User user = new User();
//        Project project = new Project();
//
//        boolean succeeded = userLogic.removeProjectFromUser(user, project);
//
//        assertFalse(succeeded);
//    }
//
//    @Test
//    public void testRemoveProjectWhenTheUserOwnsIt() {
//        User user = new User();
//        Project project = new Project();
//        project.name = "mynewproject";
//
//        userLogic.addNewProjectToUser(user, project);
//
//        boolean isRemoved = userLogic.removeProjectFromUser(user, project);
//
//        assertTrue(isRemoved);
//    }
//
//    @Test
//    public void testIfUserCanJoinOtherPersonsProject() {
//        User owner = new User();
//        Project project = new Project();
//        project.name = "mynewproject";
//
//        userLogic.addNewProjectToUser(owner, project);
//
//        User joiner = new User();
//        boolean succeeded = userLogic.addProjectToUser(joiner, project);
//
//        assertTrue(succeeded);
//    }
//
//    @Test
//    public void testIfUserCanJoinNonExistantProject() {
//        User user = new User();
//        Project project = new Project();
//        project.name = "mynewproject";
//
//        boolean succeeded = userLogic.addProjectToUser(user, project);
//
//        assertFalse(succeeded);
//    }
}
