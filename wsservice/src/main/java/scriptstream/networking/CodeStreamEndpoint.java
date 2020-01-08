package scriptstream.networking;

import com.google.gson.Gson;
import okhttp3.*;
import scriptstream.entities.User;
import scriptstream.logic.UserVerificationLogic;
import scriptstream.networking.decoding.CodeStreamMessageDecoder;
import scriptstream.networking.encoding.CodeStreamMessageEncoder;
import scriptstream.networking.entities.ChatMessage;
import scriptstream.networking.entities.CodeStreamMessage;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@ServerEndpoint(value = "/codestream/{projectuuid}/{gtoken}", decoders = CodeStreamMessageDecoder.class, encoders = CodeStreamMessageEncoder.class)
public class CodeStreamEndpoint {
    private static Map<UUID, List<Session>> projectSessions = new HashMap<UUID, List<Session>>();
    private static HashMap<String, User> users = new HashMap<String, User>();

    private UserVerificationLogic verificationLogic = new UserVerificationLogic();
    private final Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session session, @PathParam("projectuuid") String projectuuid, @PathParam("gtoken") String gtoken) throws IOException {
        User user = new User();
        user.setGToken(gtoken);

        try{
            user = verificationLogic.verify(user);
        }
        catch (IOException e) {

            session.close();
            return;
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


    }

    @OnMessage
    public void onMessage(Session session, CodeStreamMessage message, @PathParam("projectuuid") String projectuuid) throws IOException {
        message.setFrom(users.get(session.getId()).getName());
        System.out.println(session.getId());
        projectSessions.get(UUID.fromString(projectuuid)).forEach(session1 -> {
            try {
                session1.getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    @OnClose
    public void onClose(Session session, @PathParam("projectuuid") String projectuuid){
        projectSessions.get(UUID.fromString(projectuuid)).remove(session);
    }

}
