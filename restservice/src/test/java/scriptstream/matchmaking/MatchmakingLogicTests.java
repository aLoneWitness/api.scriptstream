package scriptstream.matchmaking;

import javafx.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.logic.MatchmakingLogic;
import static org.junit.jupiter.api.Assertions.*;

public class MatchmakingLogicTests {
    private MatchmakingLogic matchmakingLogic;

    @BeforeEach
    public void setUp() {
        matchmakingLogic = new MatchmakingLogic();
    }

    @Test
    public void testIfBestCompatibleGetMatched() {
        Skill skill = new Skill();
        Project compatProject = new Project();
        User user = new User();

        skill.name = "Python";
        skill.sPercentage = 20;
        compatProject.requiredSkills.add(skill);
        user.skills.add(skill);

        Skill skill2 = new Skill();
        skill2.name = "Machine Learning";
        skill2.sPercentage = 20;
        Project incompatProject = new Project();
        incompatProject.requiredSkills.add(skill2);

        matchmakingLogic.addProjectToPool(compatProject);
        matchmakingLogic.addProjectToPool(incompatProject);

        Pair<Project, Integer> match = matchmakingLogic.match(user);

        assertTrue(match.getKey().equals(compatProject));
    }
}
