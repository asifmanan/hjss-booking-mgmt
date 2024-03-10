package com.hjss.backend;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Random;


/**
 * Utility class containing helper functions and objects.
 */
public class Util {
    public enum Gender {
        Male, Female, Other, Unknown
    }

    /**
     * @param date String is passed as a parameter valid date string is in the format [YYYY-MM-DD]
     * @return true if the date matches the ISO date format and false otherwise
     */
    public static boolean isDateFormatValid(String date){
        if( date.matches("^\\d{4}(-)(0[1-9]|1[0-2])(-)(0[1-9]|[12][0-9]|3[01])$") ) {
            return true;
        }
        return false;
    }

    /**
     * @param dateString a string containing a date in a valid format.
     * @return a LocalDate object after successful parsing, Otherwise null is returned if date is invalid (such 2023-02-29) and raises an exception.
     */
    public static LocalDate convertToDate(String dateString){
        if( isDateFormatValid(dateString) ) {
            try {
                return LocalDate.parse(dateString);
            } catch (DateTimeException e) {
                System.out.println("Exception caught for String: " + e.getMessage());
            }
        }
        return null;
    }
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

    public enum GradeEnum {
        ZERO(0),
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);

        private final int value;

        GradeEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public GradeEnum increment() {
            if (this.ordinal() == GradeEnum.values().length - 1) {
                // If the current enum is FIVE, return FIVE
                return this;
            }
            // Otherwise, increment as usual
            return GradeEnum.values()[this.ordinal() + 1];
        }

        public static GradeEnum fromInt(int i) {
            for (GradeEnum num : GradeEnum.values()) {
                if (num.getValue() == i) {
                    return num;
                }
            }
            throw new IllegalArgumentException("Invalid value: " + i);
        }
    }
}
