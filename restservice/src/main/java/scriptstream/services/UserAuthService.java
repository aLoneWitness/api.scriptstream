package scriptstream.services;


import com.google.gson.Gson;
import scriptstream.entities.User;
import scriptstream.logic.UserAuthLogic;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("auth")
public class UserAuthService {
    private UserAuthLogic userAuthLogic;
    private final Gson gson = new Gson();

    @Inject
    public UserAuthService(UserAuthLogic userAuthLogic){
        this.userAuthLogic = userAuthLogic;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        try {
            String token = userAuthLogic.login(user);
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        }
        catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }


    }
}
