package view;

import model.calendar.Day;
import model.calendar.Lesson;
import model.calendar.Week;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
//TODO:ADD hours dynamically , separate mvc model ,refactoring, set font to course ,fix jump nav cal, add on click functionality ,
public class CalendarView extends Node {
    private VBox finalView;
    private VBox[] daysView = new VBox[7];

    public CalendarView(Week week) {
        int hour = 7;
        HBox calendar = new HBox(5);
        calendar.getStyleClass().add("linearStripes");

        for (int i = 0; i < 7; i++) {
            VBox vb = new VBox();
            vb.setPrefWidth(200);
            vb.getStyleClass().add("transparent");
            daysView[i] = vb;
            calendar.getChildren().add(vb);
        }
//      VIEW TEST
//        Day today = new Day();
//        today.setDate(LocalDate.now());
//        LocalTime time1 = LocalTime.of(9, 0);
//        LocalTime time2 = LocalTime.of(10, 0);
//        week.addLesson(new Lesson("SDJ", time1, time2), today.getIndexForDay());
//
//        LocalTime time3 = LocalTime.of(12, 0);
//        LocalTime time4 = LocalTime.of(14, 0);
//
//        week.addLesson(new Lesson("SDJ", time3, time4), today.getIndexForDay());
//
//        LocalTime time5 = LocalTime.of(14, 0);
//        LocalTime time6 = LocalTime.of(15, 0);
//
//        today.setDate(LocalDate.now().plusDays(1));
//        week.addLesson(new Lesson("SDJ", time5, time6), today.getIndexForDay());
//
//        LocalTime time7 = LocalTime.of(15, 10);
//        LocalTime time8 = LocalTime.of(17, 0);
//
//        week.addLesson(new Lesson("SDJ", time7, time8), today.getIndexForDay());

        putLessonWeekOnCalendar(week);
        finalView = new VBox(calendar);
    }

    private void putLessonWeekOnCalendar(Week week) {
        for (Day day : week.getDays()) {
            putLessonDayOnCalendar(day);
        }
    }

    //FIXME this needs refactoring and testing
    private void putLessonDayOnCalendar(Day day) {
        ArrayList<Lesson> currentDayLessons = day.getLessons();

        for (int i = 0; i < currentDayLessons.size(); i++) {

            //Put currrent lesson on the calendar
            Lesson lesson = currentDayLessons.get(i);
            if (i == 0) {
                LocalTime fixedStart = LocalTime.of(8, 0);
                Duration dif = Duration.between(fixedStart,lesson.getStart());
                if ((dif.toHoursPart() > 0) || (dif.toMinutesPart() > 0))
                    daysView[day.getIndexForDay()].getChildren().add(getBlockForEmpty(fixedStart, lesson.getStart()));
            }
            AnchorPaneNode lessonBlock = getBlockForLesson(lesson.getStart(), lesson.getEnd());
            daysView[day.getIndexForDay()].getChildren().add(lessonBlock);
            //Check for break
            if (i + 1 < currentDayLessons.size()) {
                Lesson nextLesson = currentDayLessons.get(i + 1);
                Duration dif = Duration.between(lesson.getEnd(), nextLesson.getStart());
                if ((dif.toHoursPart() > 0) || (dif.toMinutesPart() > 0))
                    daysView[day.getIndexForDay()].getChildren().add(getBlockForEmpty(lesson.getEnd(), nextLesson.getStart()));
            }
        }
    }

    private AnchorPaneNode getBlockForLesson(LocalTime start, LocalTime finish) {
        AnchorPaneNode ap = new AnchorPaneNode();
        ap.setPrefWidth(200);
        ap.setPrefHeight(calculateHeightForBlock(start, finish));
        ap.getStyleClass().add("SDJ");
        Text text = new Text("SDJ-1Z");
        ap.setCourse("SDJ");
        Text description = new Text(start + " -- " + finish);
        text.setFill(Color.WHITE);
        description.setFill(Color.WHITE);
        ap.getChildren().add(text);
        ap.getChildren().add(description);
        text.setLayoutX(10);
        text.setLayoutY(20);
        description.setLayoutX(10);
        description.setLayoutY(20);
        AnchorPane.setTopAnchor(text, 5.0);
        AnchorPane.setTopAnchor(description, 50.0);
        return ap;
    }

    private AnchorPaneNode getBlockForEmpty(LocalTime start, LocalTime finish) {
        AnchorPaneNode ap = new AnchorPaneNode();
        ap.setPrefWidth(200);
        ap.setPrefHeight(calculateHeightForBlock(start, finish));
        System.out.println(start);
        System.out.println(finish);
        ap.setPrefHeight(calculateHeightForBlock(start, finish));
        ap.setCourse("Break");
        return ap;
    }

    public long calculateHeightForBlock(LocalTime start, LocalTime finish) {
        //One unit is 60 --> 1 hr ,1 minute is 1 pixel
        Duration duration = Duration.between(start, finish);
        long hour = duration.toHoursPart();
        long minute = duration.toMinutesPart();
        long result = hour * 60 + minute * 1;
        return result;
    }

    public VBox getFinalView() {
        return finalView;
    }
}
