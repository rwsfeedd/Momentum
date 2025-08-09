package com.javafx.terminmanagement;

import com.google.gson.stream.JsonReader;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;

/**
 * enthält alle wichtigen Funktionen für die Arbeit mit der Mainstage
 */
public class Model {
    private static Model instance;
    private static Stage stage;//static nötig?


    private File dataDir;

    //Property für MainWindowView
    private final SimpleListProperty<Task> currentTasks;
    //Propertys für TaskCreationWindowView
    private final SimpleStringProperty newTaskNameProperty;
    private final SimpleStringProperty newTaskRepeatProperty;
    private final SimpleBooleanProperty newTaskRolloverProperty;
    //private final SimpleBooleanProperty newTaskCheckNeedProperty;

    public Model(Stage stage) {
        Model.stage = stage;
        //Initialisierung der aktuellen Taskliste
        currentTasks = new SimpleListProperty<Task>(FXCollections.observableArrayList());

        newTaskNameProperty = new SimpleStringProperty();
        newTaskNameProperty.set("");

        newTaskRepeatProperty = new SimpleStringProperty();
        newTaskRepeatProperty.set("0");

        newTaskRolloverProperty = new SimpleBooleanProperty();
        newTaskRolloverProperty.set(false);

        //newTaskCheckNeedProperty = new SimpleBooleanProperty();
        //newTaskCheckNeedProperty.set(false);
    }

    public boolean writeNewTask() {
        //Unit-Test in dem Task geschrieben wird und danach gelesen wird und die Tasks miteinander verglichen werden
        //Validierung??
        return currentTasks.add(new Task(newTaskNameProperty.getValue(), Integer.parseInt(newTaskRepeatProperty.getValue()), newTaskRolloverProperty.getValue()));
    }

    public Task readTask() {
        //richtiges File öffnen
        //File einlesen
            //Informationen in Graph speichern(aufpassen das Graph nicht zu groß wird wegen Speicher)
        File testdat = new File(new File("data"), "SimpleTaskTest.json");

        String name = "";
        int repeat = 0;
        boolean rollover = false;

        try{
            FileReader reader = new FileReader(testdat);
            JsonReader jread = new JsonReader(reader);
            jread.beginObject();

            while(jread.hasNext()) {
                switch(jread.nextName()){
                    case("name"): name = jread.nextString();
                        break;
                    case ("repeat"):
                        repeat = Integer.parseInt(jread.nextString());
                        break;
                    case ("rollover"):
                        rollover = Boolean.valueOf(jread.nextString());
                        break;
                }
            }

        }catch(Exception ex) {
            ex.printStackTrace();
        }

        return new Task(name, repeat, rollover);
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

    public SimpleListProperty<Task> getCurrentTasks() {
        return currentTasks;
    }

    public SimpleStringProperty newTaskNameProperty() {
        return newTaskNameProperty;
    }

    public SimpleStringProperty newTaskRepeatProperty() {
        return newTaskRepeatProperty;
    }

    public SimpleBooleanProperty newTaskRolloverProperty() {
        return newTaskRolloverProperty;
    }

    /*
    public SimpleBooleanProperty newTaskCheckNeedProperty() {
        return newTaskCheckNeedProperty;
    }
    */
}