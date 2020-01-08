package scriptstream.networking.decoding;

import com.google.gson.Gson;
import scriptstream.networking.entities.ChatMessage;
import scriptstream.networking.entities.CodeStreamMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class CodeStreamMessageDecoder implements Decoder.Text<CodeStreamMessage> {
    private static Gson gson = new Gson();

    public CodeStreamMessage decode(String s) throws DecodeException {
        return gson.fromJson(s, CodeStreamMessage.class);
    }

    public boolean willDecode(String s) {
        return (s != null);
    }

    public void init(EndpointConfig endpointConfig) {

    }

    public void destroy() {

    }
}
