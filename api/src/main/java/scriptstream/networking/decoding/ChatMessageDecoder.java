package scriptstream.networking.decoding;

import com.google.gson.Gson;
import scriptstream.networking.entities.ChatMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {
    private static Gson gson = new Gson();

    public ChatMessage decode(String s) throws DecodeException {
        return gson.fromJson(s, ChatMessage.class);
    }

    public boolean willDecode(String s) {
        return (s != null);
    }

    public void init(EndpointConfig endpointConfig) {

    }

    public void destroy() {

    }
}
