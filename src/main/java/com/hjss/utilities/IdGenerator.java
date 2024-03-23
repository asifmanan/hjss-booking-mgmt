package com.hjss.utilities;

import java.time.Year;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    public static ConcurrentHashMap<Class<?>, AtomicInteger> sequenceMap = new ConcurrentHashMap<>();
    public static String generateSequentialId(Class<?> clazz){
        sequenceMap.putIfAbsent(clazz, new AtomicInteger(1));
        int currentYear = Year.now().getValue() % 100;
        int sequenceNumber = sequenceMap.get(clazz).getAndIncrement();
        return String.format("%02d%05d", currentYear, sequenceNumber);
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
