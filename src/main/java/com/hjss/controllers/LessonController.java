package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.Coach;
import com.hjss.models.Learner;
import com.hjss.models.Lesson;
import com.hjss.models.WeekDayTimeSlot;
import com.hjss.utilities.Grade;

import java.util.ArrayList;
import java.util.List;

public class LessonController implements ModelController<Lesson> {
    private final ModelRegister<Lesson> lessonRegister;

    public LessonController() {
        this.lessonRegister = new ModelRegister<>();
    }

    public Lesson createObject(Grade grade, Coach coach, WeekDayTimeSlot weekDayTimeSlot){
        return new Lesson(grade, coach, weekDayTimeSlot);
    }
    public Lesson getLesson(String id){
        return lessonRegister.get(id.toUpperCase());
    }

    @Override
    public String addObject(Lesson lesson) {
        return lessonRegister.add(lesson);
    }
    @Override
    public List<Lesson> getAllObjects() {
        return new ArrayList<>(lessonRegister.getAllObjects());
    }
    public List<Lesson> filterByGrade(int grade) {
        List<Lesson> allLessons = getAllObjects();
        return allLessons.stream()
                .filter(lesson -> lesson.getGradeLevel() == grade).toList();
    }
    public int filterByGradeCount(int grade) {
        return filterByGrade(grade).size();
    }

    public List<List<Lesson>> getGradedLessons(){
        return Grade.categorizeByGrade(this.getAllObjects(), Lesson::getGradeLevel);
    }
}
