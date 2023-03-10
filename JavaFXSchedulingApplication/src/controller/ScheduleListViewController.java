package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import model.*;
import model.courses.ClassOfStudents;
import view.ViewHandler;

/**
 * The purpose of this class is used to initialize the UI elements of ScheduleList.fxml
 */
public class ScheduleListViewController extends ViewController
{

  @FXML private ListView<ClassOfStudents> listView;
  @FXML private Button confirmButton;

  private Region root;
  private Model model;
  private ViewHandler viewHandler;

  /**
   * The purpose of this method is to lunch the window and initialize all
   * controls of the window
   *
   * @param viewHandler;
   * @param model;
   * @param root;
   */
  public void init(ViewHandler viewHandler, Model model, Region root)
  {
    this.model = model;
    this.viewHandler = viewHandler;
    this.root = root;

    initListOfClasses();
    initConfirmButton();
  }

  /**
   * The purpose of this method is to initialize the confirmation button
   */
  private void initConfirmButton()
  {
    confirmButton.setDisable(true);
  }

  /**
   * The purpose of this method is to initialize a listView of classes
   *
   * Snippets of code inserted from stackOverFlow (Accessed on December 2021)
   * https://stackoverflow.com/questions/41070454/how-can-i-change-the-text-on-a-listview-that-is-holding-an-object-of-type-accoun?rq=1
   */
  private void initListOfClasses()
  {



    listView.setCellFactory(
        new Callback<ListView<ClassOfStudents>, ListCell<ClassOfStudents>>()
        {
          @Override public ListCell<ClassOfStudents> call(
              ListView<ClassOfStudents> param)
          {
            ListCell<ClassOfStudents> cell = new ListCell<ClassOfStudents>()
            {
              @Override protected void updateItem(ClassOfStudents item,
                  boolean empty)
              {
                super.updateItem(item, empty);
                if (item != null)
                {
                  setText(item.getName());
                }
                else
                {
                  setText(null);
                }
              }
            };
            return cell;
          }
        });

    ObservableList<ClassOfStudents> observableClasses = FXCollections.observableArrayList(
        model.getClasses());
    listView.setItems(observableClasses);
  }

  /**
   * The purpose of this method is to open the schedule of the selected class
   * If the selected class does not have a schedule then it just creates one
   * @param e;
   */
  @FXML public void handleMouseClick(MouseEvent e)
  {
    model.setCurrentSchedule(
        model.getScheduleByClass(listView.getSelectionModel().getSelectedItem(),
            true));
    confirmButton.setDisable(false);
  }

  /**
   * The purpose of this method is to open up the CalendarView window
   */
  @FXML public void switchToCalendar()
  {
    viewHandler.openView("CalendarView");
  }

  /**
   * The purpose of this method is to open up the MainMenu window
   */
  @FXML public void switchToMenu()
  {
    viewHandler.openView("MainMenu");
  }

  public void reset()
  {
  }

  /**
   * @return the root of the controller
   */
  public Region getRoot()
  {
    return root;
  }
}