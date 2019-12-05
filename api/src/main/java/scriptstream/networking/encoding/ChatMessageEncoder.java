package scriptstream.networking.encoding;

import com.google.gson.Gson;
import scriptstream.networking.entities.ChatMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
    private static Gson gson = new Gson();

    public String encode(ChatMessage chatMessage) throws EncodeException {
        return gson.toJson(chatMessage);
    }

    public void init(EndpointConfig endpointConfig) {

    }

    public void destroy() {

    }
}
