package info.zpss.uniwood.client.util.interfaces;

public interface Controller <M extends Model, V extends View>{
    void register();
    M getModel();
    V getView();
}