package controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import model.calendar.Lesson;
import model.rooms.BookingTime;
import model.rooms.Room;
import utilities.Logger;
import view.ViewHandler;

import java.util.ArrayList;
import java.util.Optional;

/**
 * The purpose of this class is used to initialize the UI elements of Booking.fxml
 */
public class BookingController extends ViewController
{
  @FXML TableView tableView;
  @FXML private Region root;
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

    TableColumn nameColumn = new TableColumn("Course");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
    TableColumn capacityColumn = new TableColumn("Date");
    capacityColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    TableColumn mergeColumn = new TableColumn("StartTime");
    mergeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
    TableColumn endColumn = new TableColumn("EndTime");
    endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
    TableColumn roomColumn = new TableColumn("Room");
    roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
    TableColumn room2Column = new TableColumn("Room2");
    room2Column.setCellValueFactory(new PropertyValueFactory<>("room2"));

    tableView.getColumns()
        .addAll(nameColumn, capacityColumn, mergeColumn, endColumn, roomColumn,
            room2Column);
    for (Lesson r : this.model.getAllLessons())
    {
      tableView.getItems().add(r);
      Logger.info(r.toString());
    }

  }

  /**
   * The purpose of this method is to clear all the lessons from table and
   * repopulate the table with the saved lessons
   */
  public void reset()
  {
    tableView.getItems().clear();
    for (Lesson r : this.model.getAllLessons())
    {
      tableView.getItems().add(r);
    }
  }

  /**
   * @return the root of the controller
   */
  public Region getRoot()
  {
    return root;
  }

  /**
   * The purpose of this method is to open the Main Menu window when it is called
   */
  public void back()
  {
    viewHandler.openView("MainMenu");
  }

  /**
   * The purpose of this method is to open a new window when it is called
   * In the new window there will be a label with available rooms from where
   * one or two can be selected to be booked
   */
  @FXML private void onBookButtonClick()
  {
    onClick("BookRoom");
  }

  /**
   * The purpose of this method is to cancel the booking of the selected
   * lesson
   */
  @FXML private void onUnbookButtonClick()
  {
    onClick("UnBookRoom");
  }

  /**
   * The purpose of this method is to assure the functionality of all the buttons
   * Once a button is clicked, depending on its id, different actions will occur
   * When pressed, some buttons will display a pop-up window where other buttons will have
   * their own functionality
   *
   * @param clickId the id of the button
   */
  public void onClick(String clickId)
  {
    if (clickId.equals("BookRoom")
        && tableView.getSelectionModel().getSelectedItem() == null)
    {
      //Throw alert for not selecting
    }
    else if (clickId.equals("UnBookRoom")
        && tableView.getSelectionModel().getSelectedItem() != null)
    {

      int index = tableView.getSelectionModel().getFocusedIndex();
      Lesson lesson = this.model.getAllLessons().get(index);
      BookingTime book = new BookingTime(lesson.getDate(), lesson.getStart(),
          lesson.getEnd());
      lesson.getRoom().unBook(book);
      if (lesson.getRoom2() != null)
        lesson.getRoom2().unBook(book);
      lesson.setRoom(null);
      lesson.setRoom2(null);
      reset();
      return;
    }
    else
    {
      Lesson lesson = null;
      Stage displayWindow = new Stage();
      displayWindow.setHeight(400);
      displayWindow.setWidth(400);
      displayWindow.setResizable(false);
      displayWindow.initModality(Modality.APPLICATION_MODAL);
      displayWindow.setTitle("Display Lessons");
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(12);
      ColumnConstraints column1 = new ColumnConstraints();
      column1.setHalignment(HPos.LEFT);
      grid.getColumnConstraints().add(column1);
      ColumnConstraints column2 = new ColumnConstraints();
      column2.setHalignment(HPos.LEFT);
      grid.getColumnConstraints().add(column2);
      HBox hbButtons = new HBox();
      hbButtons.setSpacing(10.0);
      grid.setAlignment(Pos.CENTER);
      Button btnCancel = new Button("Cancel");
      Button btnBook = new Button("Book");
      Button btnMerge = new Button("Book merged");
      hbButtons.getChildren().addAll(btnBook, btnMerge, btnCancel);
      switch (clickId)
      {
        case "BookRoom":
          int index = tableView.getSelectionModel().getFocusedIndex();
          ArrayList<Lesson> allLessons = this.model.getAllLessons();
          lesson = allLessons.get(index);
          break;
        default:
          break;
      }

      GridPane innerGrid = new GridPane();
      innerGrid.setAlignment(Pos.CENTER);

      Label lblName = new Label("Select room to book:");

      TableView t = new TableView();
      TableColumn nameColumn = new TableColumn("Name");
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      TableColumn capacityColumn = new TableColumn("Capacity");
      capacityColumn.setCellValueFactory(
          new PropertyValueFactory<>("capacity"));
      TableColumn mergeColumn = new TableColumn("Merge with");
      mergeColumn.setCellValueFactory(new PropertyValueFactory<>("mergeWith"));

      t.setPrefHeight(200);
      t.getColumns().addAll(nameColumn, capacityColumn, mergeColumn);
      BookingTime book = new BookingTime(lesson.getDate(), lesson.getStart(),
          lesson.getEnd());
      for (Room r : this.model.getAvailableRoomsAt(book))
      {
        t.getItems().add(r);
      }

      innerGrid.add(hbButtons, 0, 20);
      grid.add(lblName, 0, 1);
      grid.add(t, 0, 2);
      grid.add(innerGrid, 0, 3, 3, 1);
      btnCancel.setOnAction(e -> displayWindow.close());
      Lesson finalLesson = lesson;

      btnBook.setOnAction(e -> {
        int index2 = t.getSelectionModel().getFocusedIndex();
        if (finalLesson.getRoom() == null)
        {
          finalLesson.setRoom(this.model.getAvailableRoomsAt(book).get(index2));
          this.model.getAvailableRoomsAt(book).get(index2).Book(book);
          displayWindow.close();
          Logger.info(model.getRooms().toString());
        }

      });

      btnMerge.setOnAction(e -> {
        int index3 = t.getSelectionModel().getFocusedIndex();

        if (finalLesson.getRoom() == null && index3 >= 0)
        {
          Room room = this.model.getAvailableRoomsAt(book).get(index3);
          finalLesson.setRoom(room);
          room.Book(book);
          if (room.getMergeWith() != null)
          {
            Room room2 = this.model.getRoomByString(room.getMergeWith());
            if (room2.canBeBookedAt(book))
            {
              finalLesson.setRoom2(room2);
              room2.Book(book);
            }
            else
            {
              error2();
            }
          }
          else
          {
            error();
          }
          displayWindow.close();
        }

      });

      Scene scene1 = new Scene(grid, 300, 300);
      displayWindow.setScene(scene1);
      displayWindow.showAndWait();
      reset();
    }
  }

  /**
   * @return and display an error
   */
  private boolean error()
  {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Cannot book merged rooms. Only one room booked");
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }

  /**
   * @return display an error
   */
  private boolean error2()
  {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("No room to merge with. Only one room booked");
    Optional<ButtonType> result = alert.showAndWait();
    return (result.isPresent()) && (result.get() == ButtonType.OK);
  }
}
