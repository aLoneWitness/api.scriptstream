package scriptstream.matchmaking;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.logic.IMatchmakingLogic;
import scriptstream.logic.MatchmakingLogic;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MatchmakingLogicTests {
    private IMatchmakingLogic matchmakingLogic;

    @BeforeEach
    public void setUp() {
        matchmakingLogic = new MatchmakingLogic();
    }

    @Test
    public void testIfBestCompatibleGetMatched() {
        // Arrange
        Skill skill = new Skill();
        Project compatProject = new Project();
        User user = new User();
        user.uuid = UUID.randomUUID();
        user.name = "UserName";

        skill.name = "Python";
        skill.sPercentage = 20;
        compatProject.uuid = UUID.randomUUID();
        compatProject.name = "My Project";
        compatProject.requiredSkills.add(skill);
        user.skills.add(skill);

        Skill skill2 = new Skill();
        skill2.name = "Machine Learning";
        skill2.sPercentage = 20;
        Project incompatProject = new Project();
        incompatProject.uuid = UUID.randomUUID();
        incompatProject.name = "My Project";
        incompatProject.requiredSkills.add(skill2);

        // Act
        matchmakingLogic.addProjectToPool(compatProject);
        matchmakingLogic.addProjectToPool(incompatProject);

        Pair<Project, Double> match = matchmakingLogic.match(user);

        // Assert
        assertEquals(compatProject, match.getKey());
    }

    @Test
    public void testIfPercentageCalculationIsCorrect() {
        // Arrange
        Skill python = new Skill();
        python.name = "Python";
        python.sPercentage = 50;

        Skill ML = new Skill();
        ML.name = "ML";
        ML.sPercentage = 30;

        Project project = new Project();
        project.uuid = UUID.randomUUID();
        project.requiredSkills.add(python);
        project.requiredSkills.add(ML);
        project.name = "My Project";

        User user = new User();
        user.uuid = UUID.randomUUID();
        user.name = "UserName";
        user.skills.add(python);
        user.skills.add(ML);

        // Act
        matchmakingLogic.addProjectToPool(project);

        Pair<Project, Double> match =  matchmakingLogic.match(user);

        // Assert
        assertEquals(40, match.getValue().intValue());
    }

    @Test
    public void testIfInvalidProjectIsDenied() {
        // Arrange
        Project project = null;

        // Act
        boolean isAccepted = matchmakingLogic.addProjectToPool(project);

        // Assert
        assertFalse(isAccepted);
    }

    @Test
    public void testIfProjectWithoutPropertiesIsDenied() {
        // Arrange
        Project project = new Project();

        // Act
        boolean isAccepted = matchmakingLogic.addProjectToPool(project);

        // Assert
        assertFalse(isAccepted);
    }

    @Test
    public void testIfInvalidUserIsDenied() {
        // Arrange
        User user = null;

        // Act
        Object object = matchmakingLogic.match(user);

        // Assert
        assertNull(object);
    }

    @Test
    public void testIfUserWithoutPropertiesIsDenied() {
        // Arrange
        User user = new User();

        // Act
        Object object = matchmakingLogic.match(user);

        // Assert
        assertNull(object);
    }
}
