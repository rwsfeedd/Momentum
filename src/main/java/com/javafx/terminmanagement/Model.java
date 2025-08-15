package com.javafx.terminmanagement;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * enthält alle wichtigen Funktionen für die Arbeit mit der Mainstage
 */
public class Model {
    private static Model instance;
    private static Stage stage;//static nötig?

    private final File dataDir = new File("data");
    private final File fileTasks = new File(dataDir, "SimpleWriteTest.json");

    //Property für MainWindowView
    private final SimpleListProperty<Task> taskListAllProperty = new SimpleListProperty<Task>(FXCollections.observableArrayList());
    //Propertys für TaskCreationWindowView
    private final SimpleStringProperty newTaskNameProperty = new SimpleStringProperty("");
    private final SimpleStringProperty newTaskRepeatProperty = new SimpleStringProperty("1");
    private final SimpleBooleanProperty newTaskRolloverProperty = new SimpleBooleanProperty(false);
    //private final SimpleBooleanProperty newTaskCheckNeedProperty;

    public Model(Stage stage) {
        Model.stage = stage;
    }

    /**
     *
     * @param fileTasks
     * @param listTasks
     * @return Rückgabe von true, wenn alle Aufgaben erfolgreich in die Datei geschrieben wurden
     */
    public boolean writeJson(File fileTasks, List<Task> listTasks) {
        System.err.println("Ein Fehler ist aufgetreten");
        try{

            if (!fileTasks.exists()) {
                if (!fileTasks.createNewFile()) {
                    System.err.println("Datenfile konnte nicht erstellt werden!");
                }
            }

            try (FileWriter fileWriter = new FileWriter(fileTasks);
                JsonWriter jsonWriter = new JsonWriter(fileWriter)) {
                jsonWriter.setIndent("    ");
                writeTaskArray(jsonWriter, listTasks);
                jsonWriter.flush();
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return true;
    }

    public void writeTaskArray(JsonWriter jsonWriter, List<Task> listTasks) throws IOException {
        jsonWriter.beginArray();
        for (Task task : listTasks) {
            writeTask(jsonWriter, task);
        }
        jsonWriter.endArray();
    }

    public void writeTask(JsonWriter jsonWriter, Task task) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name").value(task.getName());
        jsonWriter.name("repeat").value(Integer.toString(task.getRepeat()));
        jsonWriter.name("rollover").value(Boolean.toString(task.isRollover()));
        jsonWriter.name("active").value(Boolean.toString(task.isActive()));
        jsonWriter.endObject();
    }

    public void writeNewTask() {
        //Validierung
            //ist Name nicht leerer String oder null?
            //ist Aufgabenname schon vorhanden?
            //ist Wiederholung eine Zahl?
            //bei Wiederholung Untergrenze=0 und Obergrenze?
        //wenn valide

        //neue Liste in ListProperty einlesen
        List<Task> listNew = new ArrayList<>(taskListAllProperty.getValue());

        Task task = new Task(newTaskNameProperty.getValue(), Integer.parseInt(newTaskRepeatProperty.getValue()), newTaskRolloverProperty.getValue(), true);
        taskListAllProperty.add(task);

        writeJson(fileTasks, listNew);
        //die zuletzt hinzugefügte Aufgabe wird irgendwie nie geschrieben
        //return taskListAllProperty.add(new Task(newTaskNameProperty.getValue(), Integer.parseInt(newTaskRepeatProperty.getValue()), newTaskRolloverProperty.getValue()));
    }


    public ArrayList<Task> readJson() throws Exception{
        File testdat = new File(new File("data"), "SimpleTaskTest.json");
        try (FileReader fileReader = new FileReader(testdat);
             JsonReader jsonReader = new JsonReader(fileReader)) {
            return readTasksArray(jsonReader);
        }

        //Leserechte für Datei sicherstellen sonst Fehlermeldung

    }

    public ArrayList<Task> readTasksArray(JsonReader reader) throws Exception{
        //was passiert bei leeren Array?
        ArrayList<Task> returnArray = new ArrayList<>();

        reader.beginArray();
        while(reader.hasNext()) {
           returnArray.add(readTask(reader));
        }
        reader.endArray();
        return returnArray;
    }

    public Task readTask(JsonReader reader) throws Exception{
        //richtiges File öffnen
        //File einlesen
            //Informationen in Graph speichern(aufpassen das Graph nicht zu groß wird wegen Speicher)

        String name = "";
        int repeat = 0;
        boolean rollover = false;
        boolean active = false;

        try{
            reader.beginObject();

            while(reader.hasNext()) {
                switch(reader.nextName()){
                    case("name"): name = reader.nextString();
                        break;
                    case ("repeat"):
                        repeat = Integer.parseInt(reader.nextString());
                        break;
                    case ("rollover"):
                        rollover = Boolean.parseBoolean(reader.nextString());
                        break;
                    case ("active"):
                        active = Boolean.parseBoolean(reader.nextString());
                        break;
                }
            }
            reader.endObject();

        }catch(Exception ex) {
            ex.printStackTrace();
        }

        return new Task(name, repeat, rollover, active);
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

    public SimpleListProperty<Task> getTaskListAllProperty() {
        return taskListAllProperty;
    }

    public SimpleStringProperty newTaskNameProperty() {
        return newTaskNameProperty;
    }

    public SimpleStringProperty newTaskRepeatProperty() {
        return newTaskRepeatProperty;
    }

    public SimpleBooleanProperty newTaskActiveProperty() {
        return newTaskRolloverProperty;
    }

    /*
    public SimpleBooleanProperty newTaskCheckNeedProperty() {
        return newTaskCheckNeedProperty;
    }
    */
}