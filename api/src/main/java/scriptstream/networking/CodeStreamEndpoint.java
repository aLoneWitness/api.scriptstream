package scriptstream.networking;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ServerEndpoint("/codestream/{projectuuid}")
public class CodeStreamEndpoint {
    private static Map<UUID, List<Session>> projectSessions = new HashMap<UUID, List<Session>>();

}
