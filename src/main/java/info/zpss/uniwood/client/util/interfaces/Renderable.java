package info.zpss.uniwood.client.util.interfaces;

import java.util.Vector;

public interface Renderable<T extends Item> {
    void setListData(Vector<T> listData);
    void add(T item);
    void addAll(Vector<T> listData);
    void update(T item);
    void clear();
}