package com.hjss.utilities;

public enum Rating {
    VeryDissatisfied(1),
    Dissatisfied(2),
    Ok(3),
    Satisfied(4),
    VerySatisfied(5);
    private final int value;
    Rating(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public static Rating fromInt(int i){
        for(Rating num : Rating.values()){
            if (num.getValue()==i){
                return num;
            }
        }
        throw new IllegalArgumentException("Invalid Value: " + i);
    }
    public static Rating fromString(String s){
        int i = Integer.parseInt(s);
        return Rating.fromInt(i);
    }
}
