package com.hjss.models;

import com.hjss.utilities.Grade;
import com.hjss.utilities.IdGenerator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Lesson implements Identifiable {
     private final int maxCapacity;
     private String lessonId;
     private WeekDayTimeSlot weekDayTimeSlot;
     private Grade gradeLevel;
     private Coach coach;

     public Lesson(Grade gradeLevel, Coach coach, WeekDayTimeSlot weekDayTimeSlot) {
          this.gradeLevel = gradeLevel;
          this.coach = coach;
          if (!weekDayTimeSlot.isValid()){
               throw new IllegalStateException("The DayTimeSlot object is not in a valid state, please ensure the object is properly set with appropriate dates and times.");
          }
          this.weekDayTimeSlot = weekDayTimeSlot;
          generateId();
          this.maxCapacity = 4; //setting default maxCapacity to 4
     }

     private void generateId(){
          int gradeLevel = this.gradeLevel.getValue();
          int sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
          if(sequenceNumber>=100){
               IdGenerator.resetSequenceForClass(this.getClass());
               sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
          }
          String randomSequence = IdGenerator.generateRandomSequence(5);
          this.lessonId = randomSequence + String.format("%01d%02d", gradeLevel, sequenceNumber);
     }


     public boolean isLearnerEligible(Learner learner){
          return (  learner.getGradeLevel() == this.getGradeLevel()
                  || learner.getGradeLevel() == getMinLearnerGradeRequired() );
     }
     public int getMinLearnerGradeRequired(){
          int gradeLevel = this.getGradeLevel();
          return gradeLevel-1;
     }

     public WeekDayTimeSlot getWeekDayTimeSlot(){
          return this.weekDayTimeSlot;
     }
     public LocalDate getLessonDate(){
          return this.weekDayTimeSlot.getDate();
     }
     public DayOfWeek getLessonDay(){
          return this.weekDayTimeSlot.getDayOfWeek();
     }
     public LocalTime getStartTime(){
          return this.weekDayTimeSlot.getStartTime();
     }
     public LocalTime getEndTime(){
          return this.weekDayTimeSlot.getEndTime();
     }
     public Integer getGradeLevel(){
          return this.gradeLevel.getValue();
     }
     public Grade getGrade(){
          return this.gradeLevel;
     }

     public Coach getCoach() {
          return coach;
     }

     @Override
     public String getId() {
          return this.lessonId;
     }
     public int getMaxCapacity(){
          return this.maxCapacity;
     }
}
