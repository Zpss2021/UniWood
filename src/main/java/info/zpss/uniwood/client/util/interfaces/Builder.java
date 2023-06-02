package info.zpss.uniwood.client.util.interfaces;

public interface Builder<E extends Entity> {
    void hold();

    void add(E entity);
}
