package com.hjss.servicelayer;

import com.hjss.controllers.CoachController;
import com.hjss.controllers.LessonController;
import com.hjss.controllers.TimeSlotController;
import com.hjss.models.Coach;
import com.hjss.models.Lesson;
import com.hjss.models.WeekDayTimeSlot;
import com.hjss.utilities.Grade;

import java.util.List;

public class LessonInitializer {
    private final LessonController lessonController;
    private final TimeSlotController timeSlotController;
    private final CoachController coachController;
    public LessonInitializer(LessonController lessonController,
                             TimeSlotController timeSlotController,
                             CoachController coachController){

        this.lessonController = lessonController;
        this.timeSlotController = timeSlotController;
        this.coachController = coachController;
    }
    private List<Coach> getCoaches(){
        return coachController.getAllObjects();
    }
    private Lesson createLesson(Grade grade, Coach coach, String timeSlotId){
        return lessonController.createObject(grade, coach, timeSlotId);
    }
    public void initializeLessons(){
        Grade grade = Grade.ONE;
        Coach coach = coachController.getAndRotate();
        WeekDayTimeSlot weekDayTimeSlot = timeSlotController.getAndIncrement();
        while(weekDayTimeSlot!=null){
            Lesson lesson = createLesson(grade, coach, weekDayTimeSlot.getId());
            String lessonId = lessonController.addObject(lesson);
//            System.out.println("Lesson Created with id: " + lessonId);
            coach = coachController.getAndRotate();
            grade = grade.getNext()==Grade.ZERO?grade.getNext().getNext():grade.getNext();
            weekDayTimeSlot = timeSlotController.getAndIncrement();
        }
    }
}
