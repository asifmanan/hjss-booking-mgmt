package com.hjss.modelrepository;

import com.hjss.models.Identifiable;

import java.util.*;

public class ModelRegister<T extends Identifiable> {
    private Map<String, T> register = new LinkedHashMap<>();
    private String currentKey=null;
    public String add(T object){
        String key = object.getId();
        if (this.currentKey == null){
            this.currentKey = key;
        }
        this.register.put(key, object);
        return key;
    }
    public T get(String key){
        return this.register.get(key);
    }
    public T getAndIncrement() {
        if (this.register.isEmpty() || this.currentKey == null) {
            return null;
        }

        T object = this.register.get(this.currentKey);
        this.currentKey = getNextKey(this.currentKey);
        return object;
    }
    public T getAndRotate() {
        if (register.isEmpty()) {
            return null;
        }

        if (currentKey == null) {
            currentKey = register.keySet().iterator().next();  // Start from the first key if currentKey is null
        }

        T object = register.get(currentKey);  // Get the current object

        // Get the next key or wrap to the first key if reached the end
        String nextKey = getNextKey(currentKey);
        currentKey = (nextKey == null) ? register.keySet().iterator().next() : nextKey;

        return object;
    }
    private String getNextKey(String currentKey) {
        boolean found = false;
        for (String key : this.register.keySet()) {
            if (found) {
                return key;
            }
            if (key.equals(currentKey)) {
                found = true;
            }
        }
        return null; // Return null if there's no next key (loop terminates)
    }

    public void remove(String key){
        boolean isCurrentKey = key.equals(currentKey);
        this.register.remove(key);
        // If the currentKey was removed, update the currentKey.
        if (isCurrentKey) {
            // Update currentKey to the next key, or null if no next key exists.
            currentKey = getNextKey(key);
        }
    }
    public List<T> getAllObjects(){
        return new ArrayList<>(register.values());
    }
    public Map<String, T> getRegisterCopy(){
        return new LinkedHashMap<>(register);
    }
}
