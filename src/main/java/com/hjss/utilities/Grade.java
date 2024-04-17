package com.hjss.utilities;

import com.hjss.models.Graded;

import java.util.ArrayList;
import java.util.List;

public enum Grade {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;
    private static final int maxGrade;

    static {
        maxGrade = 5;
    }

    Grade(int value) {
        this.value = value;
    }

    public static int getMaxGrade(){
        return maxGrade;
    }

    public int getValue() {
        return this.value;
    }

    public Grade increment() {
        if (this.ordinal() == Grade.values().length - 1) {
            // If the current enum is FIVE, return FIVE
            return this;
        }
        // Otherwise, increment as usual
        return Grade.values()[this.ordinal() + 1];
    }

    public static Grade fromInt(int i) {
        for (Grade num : Grade.values()) {
            if (num.getValue() == i) {
                return num;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + i);
    }

    public static Grade fromString(String s){
        int i = Integer.parseInt(s);
        return fromInt(i);
    }
    public Grade getNext() {
        int nextOrdinal = (this.ordinal() + 1) % Grade.values().length;
        return Grade.values()[nextOrdinal];
    }

    public static <T> List<List<T>> categorizeByGrade(List<T> items, Graded<T> graded) {
        int maxGrade = Grade.getMaxGrade();
        List<List<T>> itemsByGrade = new ArrayList<>(maxGrade + 1);
        for (int i = 0; i <= maxGrade; i++) {
            itemsByGrade.add(new ArrayList<>());
        }

        for (T item : items) {
            int gradeIndex = graded.getGrade(item); // Using Graded to get the grade
            if (gradeIndex >= 0 && gradeIndex <= maxGrade) {
                itemsByGrade.get(gradeIndex).add(item);
            } else {
                System.out.println("Item with out of range grade found: " + graded.getGrade(item));
            }
        }
        return itemsByGrade;
    }
}
