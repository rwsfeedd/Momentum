package com.javafx.terminmanagement.Controllers;

import com.javafx.terminmanagement.Model;
import com.javafx.terminmanagement.StartApplication;
import com.javafx.terminmanagement.Task;
import com.javafx.terminmanagement.TaskList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TaskCreationController {
    @FXML
    TextField textfieldName;
    @FXML
    RadioButton buttonSetActive;
    @FXML
    RadioButton buttonSetInactive;
    ToggleGroup groupButtonsActive;

    public void initialize() {
        //ToggleGroup isnt properly set up -> only one button should be accepted
        groupButtonsActive = new ToggleGroup();
        System.out.println("ToggleGroup instantiated: " + groupButtonsActive.toString());

        RadioButton buttonSetActive = new RadioButton();
        buttonSetActive.setToggleGroup(groupButtonsActive);
        buttonSetActive.setSelected(true);
        System.out.println("RadioButtonActive set: " + buttonSetActive.getToggleGroup().toString());

        RadioButton buttonSetInactive = new RadioButton();
        buttonSetInactive.setToggleGroup(groupButtonsActive);
        System.out.println("RadioButtonInactive set: " + buttonSetInactive.getToggleGroup().toString());

    }

    @FXML
    protected void onTaskCreateButtonClick() {
        Model model = Model.getInstance();
        Task task = new Task(textfieldName.getCharacters().toString(), true);
        model.writeTask(task);


    }

    /**
     * Hauptfenster laden und anzeigen
     */
    @FXML
    protected void onCancelButtonClick(){

        //Hauptstage vom Mastercontroller holen
        Model controller = Model.getInstance();
        Stage stage = controller.getStage();

        try{
            //Die Objekthierarchie aus dem zugehörigen XML Dokument laden
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("mainWindow-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);

            //Stage initialisieren und darstellen
            stage.setTitle("Terminmanagement");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}