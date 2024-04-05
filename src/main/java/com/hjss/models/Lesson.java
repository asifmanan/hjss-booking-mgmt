package com.hjss.models;

import com.hjss.utilities.Grade;
import com.hjss.utilities.IdGenerator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Lesson implements Identifiable {
     private final int maxCapacity;
     String lessonId;
     WeekDayTimeSlot weekDayTimeSlot;
     Grade gradeLevel;
     Coach coach;

     public Lesson(Grade gradeLevel, Coach coach, WeekDayTimeSlot weekDayTimeSlot) {
          this.gradeLevel = gradeLevel;
          this.coach = coach;
          if (!weekDayTimeSlot.isValid()){
               throw new IllegalStateException("The DayTimeSlot object is not in a valid state, please ensure the object is properly set with appropriate dates and times.");
          }
          this.weekDayTimeSlot = weekDayTimeSlot;
          generateLessonId();
          this.maxCapacity = 4; //setting default maxCapacity to 4
     }

     private void generateLessonId(){
          int gradeLevel = this.gradeLevel.getValue();
          int sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
          if(sequenceNumber>=100){
               IdGenerator.resetSequenceForClass(this.getClass());
               sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
          }
          String randomSequence = IdGenerator.generateRandomSequence(3);
          this.lessonId = "21" + randomSequence + String.format("%01d%02d", gradeLevel, sequenceNumber);
     }


     public boolean isGradeValid(Learner learner){
          return (  learner.getGradeLevel() == this.getGradeInt()
                  || learner.getGradeLevel() + 1 == this.getGradeInt() );
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
     public Integer getGradeInt(){
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
