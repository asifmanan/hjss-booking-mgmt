package com.hjss.modelrepository;

import com.hjss.models.Identifiable;

import java.util.*;

public class ModelRegister<T extends Identifiable> {
    private Map<String, T> register = new LinkedHashMap<>();
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
    public Map<String, T> getRegisterCopy(){
        return new LinkedHashMap<>(register);
    }
}
