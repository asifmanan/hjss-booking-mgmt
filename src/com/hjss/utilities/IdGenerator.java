package com.hjss.utilities;

import java.util.Random;

public class IdGenerator {
    public static String generateId(){
        final String CHAR_POOL = "abcdefghijklmnpqrsruvwxyz123456789";
        final int ID_LENGTH = 8;
        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i<ID_LENGTH; i++){
            stringBuilder.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        return stringBuilder.toString();
    }
}
