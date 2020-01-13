package scriptstream.services;

import com.google.gson.Gson;
import scriptstream.entities.Skill;
import scriptstream.entities.User;
import scriptstream.filters.JWTTokenNeeded;
import scriptstream.logic.MatchmakingLogic;
import scriptstream.logic.UserAuthLogic;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @POST
    @Path("addskill")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response addSkill(Skill skill){
        return Response.ok().build();
    }

    @POST
    @Path("matchuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response matchUser(User user){
        return Response.ok(gson.toJson(matchmakingLogic.match(user))).build();
    }

    @POST
    @Path("getmatchedprojects")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response getMatchedProjects(User user){
        return Response.ok(gson.toJson(matchmakingLogic.getMatchedProjects(user))).build();
    }
}
