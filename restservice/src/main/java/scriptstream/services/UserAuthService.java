package scriptstream.services;

import entities.User;
import scriptstream.logic.UserAuthLogic;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("auth")
public class UserAuthService {
    private UserAuthLogic userAuthLogic;

    @Inject
    public UserAuthService(){

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        if(userAuthLogic.register(user)){
            return Response.ok().build();
        }
        return Response.status(400).build();
    }
}
