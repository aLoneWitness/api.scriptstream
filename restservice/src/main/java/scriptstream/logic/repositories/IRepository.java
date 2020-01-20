package scriptstream.logic.repositories;

public interface IRepository<T> {
    void create(T t);
    T read(T t);
    void update(T t);
    boolean delete(T t);
    boolean exists(T t);
}
