package com.hjss.backend;

import java.util.ArrayList;

public class Lesson {
     int MAX_LEARNERS = 4;
     String lessonId;
     Util.GradeEnum gradeLevel;
     Coach coach;
     ArrayList<Learner> learners;

     public Lesson(Util.GradeEnum gradeLevel, Coach coach) {
          this.gradeLevel = gradeLevel;
          this.coach = coach;
          this.lessonId = generateLessonId();
     }

     private String generateLessonId(){
          return "LE" + Util.generateId();
     }
     public int getLearnerCount(){
          return learners.size();
     }
     public boolean isGradeValid(Learner learner){
          return (  learner.getGradeLevel() == this.getGradeLevel()
                  || learner.getGradeLevel() + 1 == this.getGradeLevel() );
     }
     public boolean addLearner(Learner learner){
          if(!isGradeValid(learner)) return false;
          if(getLearnerCount() < MAX_LEARNERS){
               this.learners.add(learner);
               return true;
          }
          return false;
     }
     public int getGradeLevel(){
          return this.gradeLevel.getValue();
     }
}
