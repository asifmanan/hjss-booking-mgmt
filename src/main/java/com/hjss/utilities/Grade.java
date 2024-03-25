package com.hjss.utilities;

public enum Grade {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

    Grade(int value) {
        this.value = value;
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
}
