package info.zpss.uniwood.client.util.interfaces;

public interface Builder<E extends Entity> {
    void hold();
    void add(E entity);
    E get(Integer id) throws InterruptedException;
    E build(Integer id) throws InterruptedException;
}
