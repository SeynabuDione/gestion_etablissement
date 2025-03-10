package com.groupeisi.g_etablissement.dao;

import javafx.collections.ObservableList;

public interface IRepository<T> {
    public int add(T t);
    public int update(T t);
    public int delete(int id);
    public ObservableList<T> getAll();
    public T get(int id);
}
