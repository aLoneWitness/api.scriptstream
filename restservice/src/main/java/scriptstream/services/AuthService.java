package scriptstream.services;


import com.google.gson.Gson;
import javafx.util.Pair;
import scriptstream.entities.User;
import scriptstream.filters.JWTTokenNeeded;
import scriptstream.logic.UserAuthLogic;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;

import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("auth")
public class AuthService {
    private UserAuthLogic userAuthLogic;
    private final Gson gson = new Gson();

    private Map<String, User> users = new HashMap<>();

    @Inject
    public AuthService(UserAuthLogic userAuthLogic){
        this.userAuthLogic = userAuthLogic;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        try {
            Pair<String, User> jwtUserPair = userAuthLogic.login(user);
            users.put(jwtUserPair.getKey(), jwtUserPair.getValue());
            return Response.ok(jwtUserPair.getKey()).header(AUTHORIZATION, "Bearer " + jwtUserPair.getKey()).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/verify")
    @JWTTokenNeeded
    public Response verify(@Context ContainerRequestContext containerRequestContext){
        return Response.ok().build();
    }
}
