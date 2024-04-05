package com.hjss.models;

import com.hjss.utilities.Grade;
import com.hjss.utilities.IdGenerator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class Lesson implements Identifiable {
//     int MAX_LEARNERS = 4;
     String lessonId;
     WeekDayTimeSlot weekDayTimeSlot;
     Grade gradeLevel;
     Coach coach;

     public Lesson(Grade gradeLevel, Coach coach, WeekDayTimeSlot weekDayTimeSlot) {
          this.gradeLevel = gradeLevel;
          this.coach = coach;
//          if (!weekDayTimeSlot.isValid()){
//               throw new IllegalStateException("The DayTimeSlot object is not in a valid state, please ensure the object is properly set with appropriate dates and times.");
//          }
          this.weekDayTimeSlot = weekDayTimeSlot;
          generateLessonId();
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


//     public int getLearnerCount(){
//          return learners.size();
//     }
     public boolean isGradeValid(Learner learner){
          return (  learner.getGradeLevel() == this.getGradeInt()
                  || learner.getGradeLevel() + 1 == this.getGradeInt() );
     }

//     public boolean addLearner(Learner learner){
//          if(!isGradeValid(learner) || this.learners.contains(learner)) return false;
//          if(getLearnerCount() < MAX_LEARNERS){
//               this.learners.add(learner);
//               return true;
//          }
//          return false;
//     }
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
}
