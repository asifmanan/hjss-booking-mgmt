package com.hjss.controllers;

import java.util.List;

public interface ModelController<T> {
    String addObject(T object);

    String createAndAddObject(String[] params);

    List<T> getAllObjects();
    T createObject(String[] params);
}
