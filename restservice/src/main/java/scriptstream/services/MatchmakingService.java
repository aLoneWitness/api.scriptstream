package scriptstream.services;

import com.google.gson.Gson;
import scriptstream.entities.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("match")
public class MatchmakingService {
    private final Gson gson = new Gson();

    @POST
    @Path("matchuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response matchUser(User user){
        return Response.ok().build();
    }
}
