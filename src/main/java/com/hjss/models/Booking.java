package com.hjss.models;

import com.hjss.utilities.BookingStatus;
import com.hjss.utilities.IdGenerator;
import org.threeten.extra.YearWeek;

import javax.swing.text.Utilities;
import java.time.LocalDate;

import static com.hjss.utilities.DateUtil.getYearWeekForDate;

public class Booking implements Identifiable {
    private String bookingId;
    private Learner learner;
    private Lesson lesson;
    private BookingStatus bookingStatus;
    private LocalDate createdOn;
    private LocalDate updatedOn;
    public Booking(Learner learner, Lesson lesson){
        this.learner = learner;
        this.lesson = lesson;
        this.bookingStatus = BookingStatus.Active;
        this.createdOn = LocalDate.now();
        this.updatedOn = LocalDate.now();
        this.generateId();
    }
    private void generateId(){
        YearWeek yearWeek = getYearWeekForDate(this.createdOn);
        int year = yearWeek.getYear();
        int week = yearWeek.getWeek();
        int sequence = IdGenerator.generateSequentialId(this.getClass());
        this.bookingId = String.format("%02d%02d%03d", year, week, sequence);
    }
    @Override
    public String getId() {
        return this.bookingId;
    }
    public Lesson getLesson() {
        return this.lesson;
    }
    public Learner getLearner() {
        return this.learner;
    }
}
