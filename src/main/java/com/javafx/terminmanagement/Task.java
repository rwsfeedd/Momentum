package com.javafx.terminmanagement;

public class Task {
    String name;
    boolean active;

    /**
     *
     * @param name  Name der Aufgabe
     * @param active Aktivitaetszustand
     */
    public Task(String name, boolean active) {
        this.name = name;
        this.active = active;
    }
}
