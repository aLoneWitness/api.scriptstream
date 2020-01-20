package scriptstream.logic.repositories;

import scriptstream.entities.User;
import scriptstream.logic.repositories.contexts.IContext;

public class UserRepository implements IRepository<User> {
    private IContext<User> userContext;

    public UserRepository(IContext<User> userContext){
        this.userContext = userContext;
    }

    @Override
    public void create(User user) {
        userContext.create(user);
    }

    @Override
    public User read(User user) {
        return userContext.read(user);
    }

    @Override
    public void update(User user) {
        userContext.update(user);
    }

    @Override
    public boolean delete(User user) {
        return userContext.delete(user);
    }

    @Override
    public boolean exists(User user) {
        return userContext.exists(user);
    }
}
