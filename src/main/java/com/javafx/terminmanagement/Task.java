package com.javafx.terminmanagement;

public class Task {
    String name;
    boolean active;

    /**
     *
     * @param name  Name der Aufgabe
     * @param active Aktivitaets- und Bearbeitungszustand
     */
    public Task(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public boolean isNull() {
        if (name.isEmpty()) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Aufgabenname: " + name + ", Aktiv: " + active;
    }

    public String getName() {
        return name;
    }

    public boolean getActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
