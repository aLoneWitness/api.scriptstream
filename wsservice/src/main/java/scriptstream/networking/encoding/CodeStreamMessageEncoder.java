package scriptstream.networking.encoding;

import com.google.gson.Gson;
import scriptstream.networking.entities.ChatMessage;
import scriptstream.networking.entities.CodeStreamMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class CodeStreamMessageEncoder implements Encoder.Text<CodeStreamMessage> {
    private static Gson gson = new Gson();

    public String encode(CodeStreamMessage message) throws EncodeException {
        return gson.toJson(message);
    }

    public void init(EndpointConfig endpointConfig) {

    }

    public void destroy() {

    }
}
