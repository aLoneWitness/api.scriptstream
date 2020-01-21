package scriptstream.logic.repositories;

import scriptstream.entities.Project;
import scriptstream.logic.repositories.contexts.IContext;

public class ProjectRepository implements IRepository<Project> {
    private IContext<Project> projectContext;

    public ProjectRepository(IContext<Project> projectContext){
        this.projectContext = projectContext;
    }

    @Override
    public void create(Project project) {
        projectContext.create(project);
    }

    @Override
    public Project read(Project project) {
        return projectContext.read(project);
    }

    @Override
    public void update(Project project) {
        projectContext.update(project);
    }

    @Override
    public boolean delete(Project project) {
        return projectContext.delete(project);
    }

    @Override
    public boolean exists(Project project) {
        return projectContext.exists(project);
    }
}
