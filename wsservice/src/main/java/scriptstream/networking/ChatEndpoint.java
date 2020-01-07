package scriptstream.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import scriptstream.entities.User;
import scriptstream.networking.decoding.ChatMessageDecoder;
import scriptstream.networking.encoding.ChatMessageEncoder;
import scriptstream.networking.entities.ChatMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.*;

@ServerEndpoint(value = "/chat/{projectuuid}/{gtoken}", decoders = ChatMessageDecoder.class, encoders = ChatMessageEncoder.class )
public class ChatEndpoint {
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


        UUID uuid = UUID.fromString(projectuuid);
        System.out.println(uuid);
        if(projectSessions.containsKey(UUID.fromString(projectuuid))){
            projectSessions.get(UUID.fromString(projectuuid)).add(session);
        }
        else{
            List<Session> sessions = new ArrayList<>();
            sessions.add(session);
            projectSessions.put(UUID.fromString(projectuuid), sessions);
        }


        users.put(session.getId(), user);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFrom(user.getName());
        chatMessage.setContent("Connected!");
        projectSessions.get(UUID.fromString(projectuuid)).forEach(session1 -> {
            try {
                session1.getBasicRemote().sendObject(chatMessage);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    @OnMessage
    public void onMessage(Session session, ChatMessage chatMessage, @PathParam("projectuuid") String projectuuid) throws IOException {
        chatMessage.setFrom(users.get(session.getId()).getName());
        System.out.println(session.getId());
        projectSessions.get(UUID.fromString(projectuuid)).forEach(session1 -> {
            try {
                session1.getBasicRemote().sendObject(chatMessage);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    @OnClose
    public void onClose(Session session, @PathParam("projectuuid") String projectuuid) throws IOException {
        projectSessions.get(UUID.fromString(projectuuid)).remove(session);
    }
}
