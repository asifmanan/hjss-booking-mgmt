package com.hjss.utilities;

import org.threeten.extra.YearWeek;

import java.time.DateTimeException;
import java.time.LocalDate;
/**
 * Date Utility class containing helper functions and objects related to dates and duration.
 */
public class DateUtil {
    private static final String DateFormatRegex = "^\\d{4}[-/ ](0[1-9]|1[0-2])[-/ ](0[1-9]|[12][0-9]|3[01])$";
    /**
     * @param date String is passed as a parameter valid date string is in the format [YYYY-MM-DD]
     * @return true if the date matches the ISO date format and false otherwise
     */
    public static boolean isDateFormatValid(String date){
        if( date.matches(getDateFormatRegex()) ) {
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
            String hyphenDateString = convertToHyphenFormat(dateString);
            try {
                return LocalDate.parse(hyphenDateString);
            } catch (DateTimeException e) {
//                System.out.println("Exception caught for String: " + e.getMessage());
                return null;
            }
        }
        return null;
    }
    public static String convertToHyphenFormat(String dateString) {
        return dateString.replace("/","-").replace(" ", "-");
    }
    public static boolean isDateValid(String dateString){
        try{
            LocalDate.parse(dateString);
            return true;
        } catch (DateTimeException e){
            return false;
        }
    }
    public static String getDateFormatRegex(){
        return DateFormatRegex;
    }
    public static YearWeek getYearWeekForDate(LocalDate date){
        return YearWeek.from(date);
    }
}
