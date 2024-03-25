package com.hjss.dataregistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ModelRegister<T extends Identifiable> {
    private Map<String, T> register = new HashMap<>();
    public String add(T object){
        String key = object.getId();
        this.register.put(key, object);
        return key;
    }
    public T get(String key){
        return this.register.get(key);
    }
    public void remove(String key){
        this.register.remove(key);
    }
    public List<T> getAllObjects(){
        return new ArrayList<>(register.values());
    }
}
