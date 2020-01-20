package scriptstream.logic;

import javafx.util.Pair;
import scriptstream.entities.Project;
import scriptstream.entities.User;

public interface IMatchmakingLogic {
    boolean addProjectToPool(Project project);
    Pair<Project, Double> match(User user);
}
