package scriptstream.services;

import com.google.gson.Gson;
import javafx.util.Pair;
import scriptstream.entities.Project;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.filters.JWTTokenNeeded;
import scriptstream.logic.IMatchmakingLogic;
import scriptstream.logic.MatchmakingLogic;
import scriptstream.logic.UserAuthLogic;
import scriptstream.logic.UserLogic;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("user")
public class UserService {
    @Inject
    private IMatchmakingLogic matchmakingLogic;

    @Inject
    private UserAuthLogic userAuthLogic;

    @Inject
    private UserLogic userLogic;

    private final Gson gson = new Gson();

    @GET
    @Path("getprofile")
    @JWTTokenNeeded
    public Response getProfile(@Context ContainerRequestContext context){
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        return Response.ok(gson.toJson(user)).build();
    }

    @POST
    @Path("addskill")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response addSkill(@Context ContainerRequestContext context, Skill skill){
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        if(userLogic.addSkillToUser(user, skill)){
            return Response.ok().build();
        }
        return Response.notModified().build();
    }

    @POST
    @Path("removeskill")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response removeSkill(@Context ContainerRequestContext context, Skill skill){
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        if(userLogic.removeSkillFromUser(user, skill)){
            return Response.accepted().build();
        }
        return Response.notModified().build();
    }

    @POST
    @Path("createproject")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response createProject(@Context ContainerRequestContext context, Project project) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        project.uuid = UUID.randomUUID();
        if (userLogic.addNewProjectToUser(user, project)) {
            return Response.accepted().build();
        }
        return Response.notModified().build();
    }

    @POST
    @Path("removeproject")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response removeProject(@Context ContainerRequestContext context, Project project) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        if(userLogic.removeProjectFromUser(user, project)){
            return Response.accepted().build();
        }
        return Response.notModified().build();
    }

    @POST
    @Path("leaveproject")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response leaveProject(@Context ContainerRequestContext context, Project project) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        if(userLogic.leaveProjectFromUser(user, project)) {
            return Response.accepted().build();
        }
        return Response.notModified().build();
    }

    @GET
    @Path("getjoinedprojects")
    @JWTTokenNeeded
    public Response getJoinedProjects(@Context ContainerRequestContext context) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        return Response.ok(gson.toJson(user.joinedProjects)).build();
    }

    @GET
    @Path("getownedprojects")
    @JWTTokenNeeded
    public Response getOwnedProjects(@Context ContainerRequestContext context) {
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        return Response.ok(gson.toJson(user.ownedProjects)).build();
    }

    @GET
    @Path("getskills")
    @JWTTokenNeeded
    public Response getSkills(@Context ContainerRequestContext context){
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        return Response.ok(gson.toJson(user.skills)).build();
    }

    @POST
    @Path("match")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response matchUser(@Context ContainerRequestContext context){
        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        Pair<Project, Double> match = matchmakingLogic.match(user);
        if(userLogic.addProjectToUser(user, match.getKey())){
            return Response.ok(gson.toJson(match.getKey())).build();
        }
        return Response.serverError().build();
    }

//    @GET
//    @Path("getprojects")
//    @JWTTokenNeeded
//    public Response getMatchedProjects(@Context ContainerRequestContext context){
//        User user = userLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
//        return Response.ok(gson.toJson(matchmakingLogic.getMatchedProjects(user))).build();
//    }
}
