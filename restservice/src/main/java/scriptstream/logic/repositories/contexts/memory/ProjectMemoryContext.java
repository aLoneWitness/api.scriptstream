package scriptstream.logic.repositories.contexts.memory;

import scriptstream.entities.Project;
import scriptstream.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProjectMemoryContext extends MemoryContext<Project> {
    private Map<UUID, Project> projects = new HashMap<>();

    @Override
    public void create(Project project) {
        if(projects.containsKey(project.uuid)) return;
        projects.put(project.uuid, project);
    }

    @Override
    public Project read(Project project) {
        return projects.get(project.uuid);
    }

    @Override
    public void update(Project project) {
        if(projects.containsKey(project.uuid)){
            projects.remove(project.uuid);
            projects.put(project.uuid, project);
        }
    }

    @Override
    public boolean delete(Project project) {
        projects.remove(project.uuid);
        return true;
    }

    @Override
    public boolean exists(Project project) {
        return projects.containsKey(project.uuid);
    }
}
