package com.hjss.controllers;

import com.hjss.modelrepository.ModelRegister;
import com.hjss.models.Lesson;

import java.util.List;

public class LessonController implements ModelController<Lesson> {
    private final ModelRegister<Lesson> lessonRegister;

    public LessonController() {
        this.lessonRegister = new ModelRegister<>();
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
