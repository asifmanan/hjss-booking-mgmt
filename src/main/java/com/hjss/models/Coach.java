package com.hjss.models;

import com.hjss.controllers.BookingController;
import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.Gender;
import com.hjss.utilities.IdGenerator;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public class Coach extends Person implements Identifiable {
    String coachId;
    int randomIdentifier;
    public Coach(String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        super(firstName, lastName, gender, dateOfBirth);
        generateId();
    }
    public Coach(String firstName, String lastName, Gender gender) {
        this(firstName, lastName, gender, null);
    }
    public Coach(String firstName, String lastName){
        this(firstName, lastName, Gender.Unknown, null);
    }
    private void generateId(){

        int sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
        if(sequenceNumber>=100){
            IdGenerator.resetSequenceForClass(this.getClass());
            sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
        }
        String randomString = IdGenerator.generateRandomSequence(2);
        this.randomIdentifier = sequenceNumber;
        this.coachId = "12" + randomString + String.format("%02d", sequenceNumber);
    }

    public String getId() {
        return this.coachId;
    }

    public Integer getMonthlyCoachRating(List<Booking> bookingListByMonth){
        List<Booking> attendedBookingList = bookingListByMonth.stream().
                filter(booking -> booking.getLesson().getCoach()==this
                && booking.getBookingStatus()== BookingStatus.Attended).toList();
        if(attendedBookingList.isEmpty()) return null;
        int rating = 0;
        for(Booking booking : attendedBookingList){
            rating += booking.getRating().getValue();
        }
        return rating / attendedBookingList.size();
    }

    public int getRandomIdentifier(){
        return this.randomIdentifier;
    }
}
