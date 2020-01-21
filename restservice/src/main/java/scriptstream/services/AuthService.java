package scriptstream.services;


import com.google.gson.Gson;
import scriptstream.entities.User;
import scriptstream.filters.JWTTokenNeeded;
import scriptstream.logic.UserAuthLogic;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
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
    @Consumes(APPLICATION_JSON)
    public Response login(User user) {
        try {
            AbstractMap.SimpleEntry<String, User> jwtUserPair = userAuthLogic.login(user);
            users.put(jwtUserPair.getKey(), jwtUserPair.getValue());
            return Response.ok(jwtUserPair.getKey()).header(AUTHORIZATION, "Bearer " + jwtUserPair.getKey()).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/verify")
    @JWTTokenNeeded
    public Response verify(@Context ContainerRequestContext containerRequestContext){
        return Response.ok().build();
    }
}
