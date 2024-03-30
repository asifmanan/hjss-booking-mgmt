package com.hjss.controllers;

import java.util.List;

public interface ModelController<T> {
    String addObject(T object);
    List<T> getAllObjects();
//    T createObject(String[] params);
}
