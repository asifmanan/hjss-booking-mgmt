package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.Coach;
import com.hjss.models.Lesson;
import com.hjss.utilities.Grade;

import javax.swing.text.Utilities;
import java.util.List;

public class LessonController implements ModelController<Lesson> {
    private final ModelRegister<Lesson> lessonRegister;

    public LessonController() {
        this.lessonRegister = new ModelRegister<>();
    }

    public Lesson createObject(Grade grade, Coach coach, String timeSlotId){
        return new Lesson(grade, coach, timeSlotId);
    }

    @Override
    public String addObject(Lesson lesson) {
        return lessonRegister.add(lesson);
    }
    @Override
    public List<Lesson> getAllObjects() {
        return null;
    }
}
