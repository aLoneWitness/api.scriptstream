package scriptstream.services;

import com.google.gson.Gson;
import org.apache.http.protocol.ResponseServer;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.filters.JWTTokenNeeded;
import scriptstream.logic.MatchmakingLogic;
import scriptstream.logic.UserAuthLogic;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("user")
public class UserService {
    private MatchmakingLogic matchmakingLogic;
    private UserAuthLogic userAuthLogic;
    private final Gson gson = new Gson();

    @Inject
    public UserService(MatchmakingLogic matchmakingLogic, UserAuthLogic userAuthLogic) {
        this.matchmakingLogic = matchmakingLogic;
        this.userAuthLogic = userAuthLogic;
    }

    @GET
    @Path("getprofile")
    @JWTTokenNeeded
    public Response getProfile(@Context ContainerRequestContext context){
        User user = userAuthLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));

        return Response.ok(gson.toJson(user)).build();
    }

    @POST
    @Path("addskill")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response addSkill(@Context ContainerRequestContext context, Skill skill){
        User user = userAuthLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        user.skills.add(skill);
        return Response.ok().build();
    }

    @GET
    @Path("getskills")
    @JWTTokenNeeded
    public Response getSkills(@Context ContainerRequestContext context){
        User user = userAuthLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        return Response.ok(gson.toJson(user.skills)).build();
    }

    @POST
    @Path("match")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response matchUser(@Context ContainerRequestContext context){
        User user = userAuthLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        return Response.ok(gson.toJson(matchmakingLogic.match(user))).build();
    }

    @GET
    @Path("getprojects")
    @JWTTokenNeeded
    public Response getMatchedProjects(@Context ContainerRequestContext context){
        User user = userAuthLogic.getUserByUUID(UUID.fromString((String) context.getProperty("userId")));
        return Response.ok(gson.toJson(matchmakingLogic.getMatchedProjects(user))).build();
    }
}
