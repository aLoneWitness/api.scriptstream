package scriptstream.services;


import com.google.gson.Gson;
import scriptstream.entities.User;
import scriptstream.logic.UserAuthLogic;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        if(userAuthLogic.register(user)){
            return Response.ok(gson.toJson(user), MediaType.APPLICATION_JSON).build();
        }
        return Response.status(400).build();
    }
}
