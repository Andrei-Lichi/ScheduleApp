package model;

import model.calendar.*;
import model.courses.*;
import model.rooms.BookingTime;
import model.rooms.Room;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This interface specifies which methods the class implementing it must define.
 */
public interface Model {
    // scheduleList BEGIN
    Schedule getCurrentSchedule();

    void setCurrentSchedule(Schedule currentSchedule);

    ArrayList<Lesson> getAllLessons();

    Schedule getScheduleByClass(ClassOfStudents class1, boolean createIfNoScheduleFound);

    Week getCurrentWeek();

    int getCurrentWeekIndex();

    void goNextWeek();

    void goPreviousWeek();

    boolean hasNextWeek();

    boolean hasPreviousWeek();

    ArrayList<Week> getWeekList();

    int getNumberOfWeeksBetween();

    ScheduleList getScheduleList();

    void initSemester(LocalDate startTime, LocalDate endTime);

    void initializeCurrentWeekIndex();

    int getCurrentYear();

    void initializeAllWeeks();

    void exportScheduleAsXML();

    void exportWeekAsXML(Week week);
    // scheduleList END

    // roomList BEGIN
    ArrayList<Room> getRooms();

    void addRoom(Room room);

    void removeRoom(Room room);

    void setRooms(ArrayList<Room> rooms);

    ArrayList<Room> getAvailableRoomsAt(BookingTime time);

    ArrayList<Room> getRoomsByMinimumCapacity(int minCapacity);

    ArrayList<Room> getMergeableRooms();

    ArrayList<Room> getUnMergeableRooms();

    ArrayList<Room> getAvailableRoomsByMinimumCapacity(int minCapacity, BookingTime time);

    ArrayList<Room> getAvailableAndMergeableRooms(BookingTime time);

    ArrayList<Room> getAvailableAndUnMergeableRooms(BookingTime time);

    Room getRoomByString(String name);

    void readRoomsListFromBinFile();

    void readRoomsFromTXTFile(File file);
    // roomList END

    // courseList BEGIN
    void addCourse(Course course);

    void removeCourse(Course course);

    ArrayList<Course> getCoursesByTitle(String title);

    ArrayList<Course> getCoursesByTeachers(Teacher teacher);

    ArrayList<Course> getCoursesByClass(ClassOfStudents classOfStudents);

    ArrayList<Course> getCoursesByClassName(ClassOfStudents className);

    ArrayList<Course> getCourses();

    void readCoursesFromTXTFile(File file);

    void setCurrentSelectedCourse(int currentSelectedCourse);

    int getCurrentSelectedCourse();
    // courseList END

    // classList BEGIN
    int getCurrentlySelectedClass();

    void setCurrentlySelectedClass(int currentlySelectedClass);

    void addClass(ClassOfStudents _class);

    void removeClass(ClassOfStudents _class);

    ArrayList<ClassOfStudents> getClasses();

    ArrayList<ClassOfStudents> copyClasses();
    // classList END

    // copiedWeek BEGIN
    void setCopiedWeek(Week week);

    Week getCopiedWeek();

    void removeCopiedWeek();
    // copiedWeek END
}
