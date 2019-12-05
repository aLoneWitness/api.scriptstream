package scriptstream.networking.entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessage {
    private String from;
    private String content;

    @Override
    public String toString() {
        return super.toString();
    }
}
