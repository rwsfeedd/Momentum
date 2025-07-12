package com.javafx.terminmanagement;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.LinkedList;

/**
 * enthält alle wichtigen Funktionen für die Arbeit mit der Mainstage
 */
public class Model {
    private static Model instance;
    private static Stage stage;
    private TaskList currentTasks;

    public Model(Stage stage) {
        Model.stage = stage;
        this.currentTasks = new TaskList();
    }

    public boolean writeTask(Task task) {
        return currentTasks.add(task);
    }

    public Task readTask(String name) {
        //richtiges File öffnen
        //File einlesen
            //Informationen in Graph speichern(aufpassen das Graph nicht zu groß wird wegen Speicher)

        return new Task("yeye", false);
    }

    /**
     * Gibt die einzige Instanz des Modells weiter
     *
     * @return Singleton Model
     *
     */
    public static Model getInstance() {
        //Fehler wenn keine Hauptstage übergeben wurde
        if(stage == null) {
            System.err.println("Model: Bei Programmstart wurde keine Stage uebergeben!");
            Platform.exit();
        }

        //neue Instanz von Model wird erstellt wenn noch keine Instanzen davon exisiteren
        if (instance == null) {
            instance = new Model(stage);
        }

        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public TaskList getCurrentTasks() {
        return currentTasks;
    }
}