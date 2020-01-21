package scriptstream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.logic.ProjectLogic;
import scriptstream.logic.UserLogic;
import scriptstream.logic.repositories.ProjectRepository;
import scriptstream.logic.repositories.UserRepository;
import scriptstream.logic.repositories.contexts.memory.ProjectMemoryContext;
import scriptstream.logic.repositories.contexts.memory.UserMemoryContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectLogicTests {
    private static ProjectLogic projectLogic;

    @BeforeAll
    static public void beforeEach() {
        projectLogic = new ProjectLogic(new ProjectRepository(new ProjectMemoryContext()), new UserRepository(new UserMemoryContext()));
    }

    @Test
    public void testIfInvalidSkillIsNotAddedToProject() {
        Project project = new Project();

        Skill skill = new Skill();
        skill.sPercentage = 101;
        skill.name = "1111";

        boolean isSucceeded = projectLogic.addSkillToProject(project, skill);

        assertFalse(isSucceeded);
    }

    @Test
    public void testIfValidSkillIsAddedToProject() {
        Project project = new Project();

        Skill skill = new Skill();
        skill.sPercentage = 50;
        skill.name = "ML";

        boolean isSucceeded = projectLogic.addSkillToProject(project, skill);

        assertTrue(isSucceeded);
    }

    @Test
    public void testIfUnknownSkillIsRemovableFromProject() {
        Project project = new Project();
        Skill skill = new Skill();
        skill.sPercentage = 50;
        skill.name = "ML";

        boolean isRemoved = projectLogic.removeSkillFromProject(project, skill);

        assertFalse(isRemoved);
    }

    @Test
    public void testIfKnownSkillIsRemovableFromProject() {
        Project project = new Project();
        Skill skill = new Skill();
        skill.sPercentage = 50;
        skill.name = "ML";

        projectLogic.addSkillToProject(project, skill);
        boolean isRemoved = projectLogic.removeSkillFromProject(project, skill);

        assertTrue(isRemoved);
    }

    @Test
    public void testValidContributorGetsAccepted() {
        Project project = new Project();
        project.name = "hi";
        project.uuid = UUID.randomUUID();
        User user = new User();
        user.uuid = UUID.randomUUID();
        user.name = "TestUser";

        projectLogic.togglePrivacy(project);

        boolean isSucceeded = projectLogic.addContributor(project, user);

        assertTrue(isSucceeded);
    }

    @Test
    public void testContributorsCantGoOver7() {
        Project project = new Project();
        User user = new User();
        user.uuid = UUID.randomUUID();
        user.name = "TestUser";

        User user2 = new User();
        user2.uuid = UUID.randomUUID();
        user2.name = "TestUser";

        User user3 = new User();
        user3.uuid = UUID.randomUUID();
        user3.name = "TestUser";

        User user4 = new User();
        user4.uuid = UUID.randomUUID();
        user4.name = "TestUser";

        User user5 = new User();
        user5.uuid = UUID.randomUUID();
        user5.name = "TestUser";

        User user6 = new User();
        user6.uuid = UUID.randomUUID();
        user6.name = "TestUser";

        User user7 = new User();
        user7.uuid = UUID.randomUUID();
        user7.name = "TestUser";

        User user8 = new User();
        user8.uuid = UUID.randomUUID();
        user8.name = "TestUser";

        projectLogic.addContributor(project, user);
        projectLogic.addContributor(project, user2);
        projectLogic.addContributor(project, user3);
        projectLogic.addContributor(project, user4);
        projectLogic.addContributor(project, user5);
        projectLogic.addContributor(project, user6);
        projectLogic.addContributor(project, user7);
        boolean overMaxReached = projectLogic.addContributor(project, user8);

        assertFalse(overMaxReached);
    }

    @Test
    public void testContributorsSameUsersAddedTwice() {
        Project project = new Project();
        User user = new User();
        user.uuid = UUID.randomUUID();
        user.name = "TestUser";

        projectLogic.addContributor(project, user);
        boolean isAdded = projectLogic.addContributor(project, user);

        assertFalse(isAdded);
    }

    @Test
    public void testDisbandProjectNormally() {
        Project project = new Project();
        project.name = "myproject";
        User user = new User();
        user.uuid = UUID.randomUUID();
        projectLogic.createNewProject(project, user);
        project = projectLogic.getProjectByUUID(user.ownedProjects.get(0));

        boolean isDisbanded = projectLogic.disbandProject(project);

        assertTrue(isDisbanded);
    }

    @Test
    public void testRemoveContributorNormally() {
        Project project = new Project();
        User user = new User();
        user.uuid = UUID.randomUUID();
        user.name = "TestUser";

        projectLogic.addContributor(project, user);
        boolean isRemoved = projectLogic.removeContributor(project, user);

        assertFalse(isRemoved);
    }

    @Test
    public void testRemoveContributorWhoNeverJoined() {
        Project project = new Project();
        User user = new User();
        user.uuid = UUID.randomUUID();
        user.name = "TestUser";

        boolean isRemoved = projectLogic.removeContributor(project, user);

        assertFalse(isRemoved);
    }

    @Test
    public void testTogglePrivacyJoinableWhenPublic() {
        Project project = new Project();
        project.name = "myproject";
        project.uuid = UUID.randomUUID();
        User user = new User();
        user.uuid = UUID.randomUUID();
        projectLogic.createNewProject(project, user);
        project = projectLogic.getProjectByUUID(user.ownedProjects.get(0));


        User joiner = new User();
        joiner.uuid = UUID.randomUUID();
        joiner.name = "JOiner";

        projectLogic.togglePrivacy(project);
        boolean isJoinable = projectLogic.addContributor(project, joiner);

        assertTrue(isJoinable);
    }

    @Test
    public void testTogglePrivacyUnjoinableWhenPrivate() {
        Project project = new Project();
        project.name = "myproject";
        User user = new User();
        user.uuid = UUID.randomUUID();
        projectLogic.createNewProject(project, user);
        project = projectLogic.getProjectByUUID(user.ownedProjects.get(0));


        User joiner = new User();
        joiner.uuid = UUID.randomUUID();
        joiner.name = "JOiner";

        projectLogic.togglePrivacy(project);
        projectLogic.togglePrivacy(project);
        boolean isJoinable = projectLogic.addContributor(project, user);

        assertFalse(isJoinable);
    }
}
