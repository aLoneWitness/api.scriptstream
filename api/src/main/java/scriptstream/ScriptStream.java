package scriptstream;

import scriptstream.entities.Project;
import scriptstream.entities.User;

import java.util.HashMap;
import java.util.UUID;

public class ScriptStream {
    private HashMap<UUID, Project> projects = new HashMap<UUID, Project>();
    private HashMap<UUID, User> users = new HashMap<UUID, User>();

    public Project getProjectByUUID(UUID uuid) {
        return projects.get(uuid);
    }

    public User getUserByUUID(UUID uuid) {
        return users.get(uuid);
    }
}
