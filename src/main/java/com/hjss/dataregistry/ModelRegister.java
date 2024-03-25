package com.hjss.dataregistry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ModelRegister<T> {
    private Map<String, T> register = new HashMap<>();
    private AtomicInteger id = new AtomicInteger(1);
    public String add(T object){
        String key = String.valueOf(id.getAndIncrement());
        this.register.put(key, object);
        return key;
    }
    public T get(String key){
        return this.register.get(key);
    }
    public void remove(String key){
        this.register.remove(key);
    }
}
