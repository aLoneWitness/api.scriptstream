package scriptstream.networking;

import scriptstream.entities.User;
import scriptstream.networking.decoding.ChatMessageDecoder;
import scriptstream.networking.encoding.ChatMessageEncoder;
import scriptstream.networking.entities.ChatMessage;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@ServerEndpoint(value = "/chat/{projectuuid}", decoders = ChatMessageDecoder.class, encoders = ChatMessageEncoder.class )
public class ChatEndpoint {
    private Session session;

    private static HashMap<UUID, ChatEndpoint> projectMemberEndpoints = new HashMap<UUID, ChatEndpoint>();
    private static HashMap<String, User> users = new HashMap<String, User>();


    @OnOpen
    public void onOpen(Session session, @PathParam("projectuuid") String projectuuid) throws IOException {
        this.session = session;
        projectMemberEndpoints.put(UUID.fromString(projectuuid), this);

        User user = new User();
        user.setName("Mark");

        users.put(session.getId(), user);

        try {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setFrom(user.getName());
            chatMessage.setContent("Connected!");
            projectMemberEndpoints.get(UUID.fromString(projectuuid)).session.getBasicRemote().sendObject(chatMessage);
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, ChatMessage chatMessage, @PathParam("projectuuid") String projectuuid) throws IOException {
        try {
            System.out.println(session.getId());
            chatMessage.setFrom(users.get(session.getId()).getName());
            projectMemberEndpoints.get(UUID.fromString(projectuuid)).session.getBasicRemote().sendObject(chatMessage);
            System.out.println(projectMemberEndpoints.get(UUID.fromString(projectuuid)));
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }
}
