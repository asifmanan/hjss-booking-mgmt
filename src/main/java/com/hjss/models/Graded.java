package com.hjss.models;

@FunctionalInterface
public interface Graded<T> {
    public int getGrade(T item);
}
