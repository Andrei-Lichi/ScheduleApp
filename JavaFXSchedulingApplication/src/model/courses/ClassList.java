package model.courses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The purpose of this class is to store a list of ClassOfStudents objects in order
 * to manage them
 */
public class ClassList implements Serializable
{
  private ArrayList<ClassOfStudents> classList;
  private int currentlySelectedClass;

  /**
   * The purpose of the constructor is to initialize the classList ArrayList
   */
  public ClassList()
  {
    this.classList = new ArrayList<>();
  }

  /**
   * The purpose of this method is set the int value of currentlySelectedClass
   * when a class is clicked/selected
   *
   * @param currentlySelectedClass index of the class in the array
   */
  public void setCurrentlySelectedClass(int currentlySelectedClass)
  {
    this.currentlySelectedClass = currentlySelectedClass;
  }

  /**
   * @return the int value of currentlySelectedClass
   */
  public int getCurrentlySelectedClass()
  {
    return currentlySelectedClass;
  }

  /**
   * The purpose of this method is to add a class to the
   * classList ArrayList and then sort the classes by their name using a
   * compare method
   * @param  _class the class object which will be added
   */
  public void addClass(ClassOfStudents _class)
  {
    classList.add(_class);
    classList.sort(new Comparator<ClassOfStudents>()
    {
      @Override public int compare(ClassOfStudents class1,
          ClassOfStudents class2)
      {
        return class1.getName().compareTo(class2.getName());
      }
    });
  }

  /**
   * The purpose of this method is to remove a class from the
   * classList ArrayList
   * @param _class the class object which will be removed
   *
   */
  public void removeClass(ClassOfStudents _class)
  {
    try
    {
      classList.remove(_class);
    }
    catch (IndexOutOfBoundsException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * @return a list of ClassOfStudents objects
   */
  public ArrayList<ClassOfStudents> getClasses()
  {
    return classList;
  }

  /**
   *  @return a copy of the list of ClassOfStudents objects
   */
  public ArrayList<ClassOfStudents> copyClasses()
  {
    return new ArrayList<ClassOfStudents>(classList);
  }
}
