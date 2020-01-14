package scriptstream.repositories;

import scriptstream.entities.User;

import java.util.*;

public class UserRepository implements IRepository<User> {
    private Map<UUID, User> users = new HashMap<>();

    @Override
    public void create(User user) {
        if(users.containsKey(user.uuid)) return;
        users.put(user.uuid, user);
    }

    @Override
    public User read(User user) {
        return users.get(user.uuid);
    }

    @Override
    public void update(User user) {
        if(users.containsKey(user.uuid)){
            users.remove(user.uuid);
            users.put(user.uuid, user);
        }
    }

    @Override
    public boolean delete(User user) {
        users.remove(user.uuid);
        return true;
    }

    @Override
    public boolean exists(User user) {
        return users.containsKey(user.uuid);
    }
}
