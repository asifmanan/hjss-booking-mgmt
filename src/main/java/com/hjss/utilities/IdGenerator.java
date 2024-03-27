package com.hjss.utilities;

import java.time.Year;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    public static ConcurrentHashMap<Class<?>, AtomicInteger> sequenceMap = new ConcurrentHashMap<>();
    public static int generateSequentialId(Class<?> clazz){
        sequenceMap.putIfAbsent(clazz, new AtomicInteger(1));
        return sequenceMap.get(clazz).getAndIncrement();
    }
    public static String generateRandomId(){
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
