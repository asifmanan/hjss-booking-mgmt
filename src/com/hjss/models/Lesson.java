package com.hjss.models;

import com.hjss.utilities.Grade;
import com.hjss.utilities.IdGenerator;

import java.util.ArrayList;

public class Lesson {
     int MAX_LEARNERS = 4;
     String lessonId;
     Grade gradeLevel;
     Coach coach;
     ArrayList<Learner> learners;

     public Lesson(Grade gradeLevel, Coach coach) {
          this.gradeLevel = gradeLevel;
          this.coach = coach;
          this.lessonId = generateLessonId();
          this.learners = new ArrayList<Learner>();
     }

     private String generateLessonId(){
          return "LE" + IdGenerator.generateId();
     }
     public int getLearnerCount(){
          return learners.size();
     }
     public boolean isGradeValid(Learner learner){
          return (  learner.getGradeLevel() == this.getGradeLevel()
                  || learner.getGradeLevel() + 1 == this.getGradeLevel() );
     }

     public String getLessonId() {
          return lessonId;
     }

     public boolean addLearner(Learner learner){
          if(!isGradeValid(learner) || this.learners.contains(learner)) return false;
          if(getLearnerCount() < MAX_LEARNERS){
               this.learners.add(learner);
               return true;
          }
          return false;
     }
     public int getGradeLevel(){
          return this.gradeLevel.getValue();
     }

     public Coach getCoach() {
          return coach;
     }
}
