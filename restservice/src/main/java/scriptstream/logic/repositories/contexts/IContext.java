package scriptstream.logic.repositories.contexts;

public interface IContext<T> {
    void create(T t);
    T read(T t);
    void update(T t);
    boolean delete(T t);
    boolean exists(T t);
}
