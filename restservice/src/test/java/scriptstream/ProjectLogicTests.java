package scriptstream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.logic.ProjectLogic;
import scriptstream.logic.UserLogic;
import scriptstream.logic.repositories.ProjectRepository;
import scriptstream.logic.repositories.UserRepository;
import scriptstream.logic.repositories.contexts.memory.ProjectMemoryContext;
import scriptstream.logic.repositories.contexts.memory.UserMemoryContext;
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
}
