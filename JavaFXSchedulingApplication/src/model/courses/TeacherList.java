package model.courses;

import model.courses.Teacher;

import java.io.Serializable;
import java.util.ArrayList;

public class TeacherList implements Serializable
{
  ArrayList<Teacher> teachers;

  public TeacherList()
  {
    this.teachers = new ArrayList<>();
  }

  public TeacherList(ArrayList<Teacher> teachers) {
    this.teachers = teachers;
  }

  public int size() {
    return teachers.size();
  }

  public Teacher getTeacherByIndex(int index) {
    return teachers.get(index);
  }

  public void addTeacher(Teacher teacher)
  {
    this.teachers.add(teacher);
  }

  public void removeTeacher(Teacher teacher)
  {
    teachers.remove(teacher);
  }

  @Override
  public String toString() {
    String all = "";
    for(int i = 0; i<teachers.size(); i++){
      if(i!=teachers.size()-1){
        all+= teachers.get(i).getName()+", ";
      }
      else{
        all+= teachers.get(i).getName();
      }

    }
    return all;
  }
}
