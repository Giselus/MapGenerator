package sample;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.HashSet;
import java.util.Set;

public class KeyPolling {

    private static Scene scene;
    private static final Set<KeyCode> keysCurrentlyDown = new HashSet<>();
    private static final Set<MouseButton> mouseKeysCurrentlyDown = new HashSet<>();

    private static double mouseX;
    private static double mouseY;




    public KeyPolling(){

    }
    public static void pollScene(Scene scene){
        clearKeys();
        removeCurrentKeyHandlers();
        setScene(scene);
    }

    private static void clearKeys(){
        keysCurrentlyDown.clear();
        mouseKeysCurrentlyDown.clear();
    }

    private static void removeCurrentKeyHandlers(){
        if(scene != null){
            KeyPolling.scene.setOnKeyPressed(null);
            KeyPolling.scene.setOnKeyReleased(null);
            KeyPolling.scene.setOnMouseMoved(null);
            KeyPolling.scene.setOnMouseDragged(null);
            KeyPolling.scene.setOnMousePressed(null);
            KeyPolling.scene.setOnMouseReleased(null);
        }
    }

    private static void setScene(Scene scene){
        KeyPolling.scene = scene;
        KeyPolling.scene.setOnKeyPressed((keyEvent -> {
            keysCurrentlyDown.add(keyEvent.getCode());
        }));
        KeyPolling.scene.setOnKeyReleased((keyEvent -> {
            keysCurrentlyDown.remove(keyEvent.getCode());
        }));
        KeyPolling.scene.setOnMouseMoved((mouseEvent -> {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        }));
        KeyPolling.scene.setOnMouseDragged((mouseEvent -> {
            mouseX = mouseEvent.getX();
            mouseY = mouseEvent.getY();
        }));
        KeyPolling.scene.setOnMousePressed((mouseEvent -> {
            mouseKeysCurrentlyDown.add(mouseEvent.getButton());
        }));
        KeyPolling.scene.setOnMouseReleased((mouseEvent ->{
            mouseKeysCurrentlyDown.remove(mouseEvent.getButton());
        }));
    }

    public static boolean isDown(KeyCode keyCode){
        return keysCurrentlyDown.contains(keyCode);
    }

    public static boolean isDown(MouseButton mouseButton){
        return mouseKeysCurrentlyDown.contains(mouseButton);
    }

    public static double getMouseX(){
        return mouseX;
    }
    public static double getMouseY(){
        return mouseY;
    }
}
