package com.hjss.models;

import com.hjss.utilities.Grade;
import com.hjss.utilities.IdGenerator;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.IsoFields;
import java.util.ArrayList;

public class Lesson {
//     int MAX_LEARNERS = 4;
     String lessonId;
     LocalDate lessonDate;
     Grade gradeLevel;
     Coach coach;

     public Lesson(Grade gradeLevel, Coach coach, LocalDate lessonDate) {
          this.gradeLevel = gradeLevel;
          this.coach = coach;
          this.lessonDate = lessonDate;
          generateLessonId(lessonDate);
     }

     private void generateLessonId(LocalDate lessonDate){
          int year = lessonDate.get(IsoFields.WEEK_BASED_YEAR) % 100;
          int weekOfYear = lessonDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
          int sequenceNumber = IdGenerator.generateSequentialId(this.getClass());
          this.lessonId = "LE" + year + weekOfYear + sequenceNumber;
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
}
