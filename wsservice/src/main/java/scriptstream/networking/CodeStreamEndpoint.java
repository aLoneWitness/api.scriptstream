package scriptstream.networking;

import com.google.gson.Gson;
import okhttp3.*;
import scriptstream.entities.User;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ServerEndpoint("/codestream/{projectuuid}/{gtoken}")
public class CodeStreamEndpoint {
    private static Map<UUID, List<Session>> projectSessions = new HashMap<UUID, List<Session>>();
    private static HashMap<String, User> users = new HashMap<String, User>();

    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session session, @PathParam("projectuuid") String projectuuid, @PathParam("gtoken") String gtoken) throws IOException {
        User user = new User();
        user.setGToken(gtoken);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(user));

        Request request = new Request.Builder()
                .url("http://localhost:2000/rest/auth/login")
                .post(requestBody)
                .build();

        try(Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            user = gson.fromJson(response.body().string(), User.class);
            user.setName(user.getName());
        }
        catch(IOException e){
            session.close();
        }


    }

    @OnClose
    public void onClose(Session session){

    }

}
