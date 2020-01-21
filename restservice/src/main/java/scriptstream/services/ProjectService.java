package scriptstream.services;

import com.google.gson.Gson;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.filters.JWTTokenNeeded;
import scriptstream.logic.MatchmakingLogic;
import scriptstream.logic.ProjectLogic;
import scriptstream.logic.UserLogic;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("project")
public class ProjectService {
    private final Gson gson = new Gson();

    @Inject
    private UserLogic userLogic;

    @Inject
    private ProjectLogic projectLogic;

    @Inject
    private MatchmakingLogic matchmakingLogic;

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response createProject(@Context ContainerRequestContext context, Project project) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        project.uuid = UUID.randomUUID();
//        if (userLogic.addNewProjectToUser(user, project)) {
//            return Response.accepted().build();
//        }
        if(projectLogic.createNewProject(project, user)){
            return Response.accepted().build();
        }
        return Response.notModified().build();
    }

    @POST
    @Path("remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response removeProject(@Context ContainerRequestContext context, Project project) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
//        if(userLogic.removeProjectFromUser(user, project)){
//            return Response.accepted().build();
//        }
        if(projectLogic.removeContributor(project, user)){
            return Response.accepted().build();
        }
        return Response.notModified().build();
    }

    @POST
    @Path("addskill")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response addSkill(@Context ContainerRequestContext context, Skill skill, @QueryParam("projectuuid") String projectuuid){
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        Project project = projectLogic.getProjectByUUID(UUID.fromString(projectuuid));
        // CONTAINS USES EQUALS IN BACKGROUND???
        if(!project.owner.equals(user)) {
            return Response.status(403).build();
        }

        if(projectLogic.addSkillToProject(project, skill)){
            return Response.accepted().build();
        }
        return Response.status(422).build();
    }

    @POST
    @Path("leave")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response leaveProject(@Context ContainerRequestContext context, Project project) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
//        if(userLogic.leaveProjectFromUser(user, project)) {
//            return Response.accepted().build();
//        }
        if(projectLogic.removeContributor(project, user)){
            return Response.accepted().build();
        }
        return Response.notModified().build();
    }

    @GET
    @Path("getjoined")
    @JWTTokenNeeded
    public Response getJoinedProjects(@Context ContainerRequestContext context) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        List<Project> projects = new ArrayList<>();
        for (UUID uuid: user.joinedProjects) {
            projects.add(projectLogic.getProjectByUUID(uuid));
        }
        return Response.ok(gson.toJson(projects)).build();
    }

    @GET
    @Path("getowned")
    @JWTTokenNeeded
    public Response getOwnedProjects(@Context ContainerRequestContext context) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        List<Project> projects = new ArrayList<>();
        for (UUID uuid: user.ownedProjects) {
            projects.add(projectLogic.getProjectByUUID(uuid));
        }
        return Response.ok(gson.toJson(projects)).build();
    }

    @POST
    @Path("toggleprivacy")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response togglePrivacy(@Context ContainerRequestContext context, Project project) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
//        if(user.ownedProjects.stream().anyMatch(project1 -> project1.equals(project))){
//            Project publicProject = user.ownedProjects.stream().filter(project1 -> project1.equals(project)).findFirst().get();
//            user.ownedProjects.remove(publicProject);
//            if(publicProject.isPublic){
//                publicProject.isPublic = false;
//                matchmakingLogic.removeProjectFromPool(publicProject);
//            }
//            else{
//                publicProject.isPublic = true;
//                matchmakingLogic.addProjectToPool(publicProject);
//            }
//            user.ownedProjects.add(publicProject);
//            return Response.accepted().build();
//        }
//        else {
//            return Response.notModified().build();
//        }
        if(projectLogic.togglePrivacy(project)){
            return Response.accepted().build();
        }
        return Response.notModified().build();
    }
}
