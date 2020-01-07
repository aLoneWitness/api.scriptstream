package scriptstream.networking;

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

    @OnOpen
    public void onOpen(Session session, @PathParam("projectuuid") String projectuuid, @PathParam("gtoken") String gtoken) throws IOException {

    }

    @OnClose
    public void onClose(Session session){

    }
}
