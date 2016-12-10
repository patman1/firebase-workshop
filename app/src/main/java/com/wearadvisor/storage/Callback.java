package com.wearadvisor.storage;

public interface Callback<T> {
    void on(T t);
}
