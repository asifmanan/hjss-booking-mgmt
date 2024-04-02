package com.hjss.views;

import com.hjss.controllers.LessonController;
import org.jline.builtins.Less;

import java.util.Arrays;
import java.util.List;

public class LessonListView {
    private LessonController lessonController;
    private List<String> headers;

    public LessonListView(LessonController lessonController){
        this.lessonController = lessonController;
        this.headers = Arrays.asList("Id","Date", "Grade", "Coach Name", "");
    }
}
