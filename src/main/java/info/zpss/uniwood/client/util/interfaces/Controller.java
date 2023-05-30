package info.zpss.uniwood.client.util.interfaces;

public interface Controller<M extends Model, V extends View> {
    M getModel();

    V getView();

    void register();
}