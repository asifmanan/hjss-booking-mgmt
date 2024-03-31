package com.hjss.models;

import com.hjss.utilities.Grade;
import com.hjss.utilities.IdGenerator;

public class Lesson implements Identifiable {
//     int MAX_LEARNERS = 4;
     String lessonId;
     WeekDayTimeSlot WeekdayTimeSlot;
     Grade gradeLevel;
     Coach coach;

     public Lesson(Grade gradeLevel, Coach coach, WeekDayTimeSlot WeekdayTimeSlot) {
          this.gradeLevel = gradeLevel;
          this.coach = coach;
          if (!WeekdayTimeSlot.isValid()){
               throw new IllegalStateException("The DayTimeSlot object is not in a valid state, please ensure the object is properly set with appropriate dates and times.");
          }
          this.WeekdayTimeSlot = WeekdayTimeSlot;
          generateLessonId();
     }

     private void generateLessonId(){
          int currentYear = this.WeekdayTimeSlot.getYear() % 100;
          int currentWeekOfYear = this.WeekdayTimeSlot.getWeek();
          int sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
          this.lessonId = "LR" + String.format("%02d%02d%02d", currentYear, currentWeekOfYear,sequenceNumber);
     }
//     public int getLearnerCount(){
//          return learners.size();
//     }
     public boolean isGradeValid(Learner learner){
          return (  learner.getGradeLevel() == this.getGradeLevel()
                  || learner.getGradeLevel() + 1 == this.getGradeLevel() );
     }

     public String getLessonId() {
          return lessonId;
     }

//     public boolean addLearner(Learner learner){
//          if(!isGradeValid(learner) || this.learners.contains(learner)) return false;
//          if(getLearnerCount() < MAX_LEARNERS){
//               this.learners.add(learner);
//               return true;
//          }
//          return false;
//     }
     public int getGradeLevel(){
          return this.gradeLevel.getValue();
     }

     public Coach getCoach() {
          return coach;
     }

     @Override
     public String getId() {
          return this.lessonId;
     }
}
