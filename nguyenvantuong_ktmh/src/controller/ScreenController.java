package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenController {
    private HashMap<String, Parent> screenMap = new HashMap<>();

    public HashMap<String, Parent> getScreenMap() {
        return screenMap;
    }

    private Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    public void addScreen(String name, Parent pane){

        screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){

        main.setRoot( screenMap.get(name) );
    }
}