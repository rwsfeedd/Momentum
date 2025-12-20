package com.javafx.terminmanagement.Controllers;

import com.javafx.terminmanagement.Model;
import com.javafx.terminmanagement.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskChangeController {
    @FXML
    private Label labelName;
    @FXML
    private TextField textfieldRepeat;
    @FXML
    private RadioButton buttonSetRolloverOn;
    @FXML
    private RadioButton buttonSetRolloverOff;
    @FXML
    private Label validationLabel;

    /**
     * Initialisierung des Aufgabenerstellungsfensters
     */
    public void initialize() {
        Model model = Model.getInstance();

        //Validierungslabel bereinigen
        model.setNewTaskValidationProperty("");

        //Binding von Aufgabenname
        labelName.textProperty().bind(model.newTaskNameProperty());
        //Binding von Aufgabenwiederholung
        model.newTaskRepeatProperty().bindBidirectional(textfieldRepeat.textProperty());
        //Binding von Aufgabenrollover
        model.newTaskRolloverProperty().bindBidirectional(buttonSetRolloverOn.selectedProperty());
        buttonSetRolloverOff.selectedProperty().bind(model.newTaskRolloverProperty().not());
        //Binding für Label um Nutzer invalide Aufgabe zu zeigen
        validationLabel.textProperty().bind(model.newTaskValidationProperty());

        model.loadChangeTask();
    }

    /**
     * Knopf um erstellte Aufgabe zu Speichern
     */
    @FXML
    public void onSaveButtonClick() {
        Model model = Model.getInstance();
        if (model.writeChangedTask()) {

            Stage stage = model.getStage();
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

    /**
     * Knopf um Aufgabenübersicht anzuzeigen
     */
    @FXML
    public void onCancelButtonClick() {
        Model model = Model.getInstance();

        Stage stage = model.getStage();

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
