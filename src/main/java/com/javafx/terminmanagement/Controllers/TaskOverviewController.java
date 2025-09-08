package com.javafx.terminmanagement.Controllers;

import com.javafx.terminmanagement.Model;
import com.javafx.terminmanagement.StartApplication;
import com.javafx.terminmanagement.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class TaskOverviewController {
    @FXML
    private ListView<Task> taskList;

    public void initialize() {
        Model model = Model.getInstance();
        taskList.itemsProperty().bindBidirectional(model.taskListAllProperty());
        model.selectedTaskProperty().bind(taskList.selectionModelProperty().get().selectedItemProperty());
    }

    /**
     * Knopf um Aufgabenerstellungsfenster zu laden und anzuzeigen
     */
    @FXML
    public void onTaskCreateButtonClick() {

        //Hauptstage vom Model holen
        Model model = Model.getInstance();
        Stage stage = model.getStage();

        try {
            //Die Objekthierarchie aus dem zugehörigen XML Dokument laden
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("taskCreationView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);

            //Stage initialisieren und darstellen
            stage.setTitle("Terminmanagement");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Knopf um Aufgabenbearbeitungsfenster zu laden und anzuzeigen
     */
    @FXML
    public void onTaskChangeButtonClick() {

        //Hauptstage vom Model holen
        Model model = Model.getInstance();
        Stage stage = model.getStage();

        if (model.selectedTaskProperty().getValue() == null) {
            return;
        }
        try {
            //Die Objekthierarchie aus dem zugehörigen XML Dokument laden
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("taskChangeView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);

            //Stage initialisieren und darstellen
            stage.setTitle("Terminmanagement");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onTaskDeleteButtonClick() {
        Model model = Model.getInstance();
        if (!model.writeDeletedTask()) {
            System.out.println("Fehler beim Löschen der Aufgabe!");
        }

    }

    /**
     * Knopf um ausgewählte Aufgabe in Tagesplan einzutragen
     */
    @FXML
    public void onTaskSignInButtonClick() {
        Model model = Model.getInstance();
        if (!model.writeSignInTask()) {
            System.out.println("Fehler beim Eintragen der Aufgabe in den Aufgabenplan");
        }
    }

    /**
     * Knopf um Hauptfenster zu laden und anzuzeigen
     */
    @FXML
    public void onReturnButtonClick() {
        //Hauptstage vom Model holen
        Model controller = Model.getInstance();
        Stage stage = controller.getStage();

        try {
            //Die Objekthierarchie aus dem zugehörigen XML Dokument laden
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("mainWindowView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);

            //Stage initialisieren und darstellen
            stage.setTitle("Terminmanagement");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
