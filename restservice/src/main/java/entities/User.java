package entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class User {
    private UUID uuid;
    private String name;
    private String gToken;
}
