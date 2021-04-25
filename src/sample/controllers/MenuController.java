package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sample.KeyPolling;
import sample.Main;

import java.io.*;

public class MenuController {

    @FXML
    VBox mapBox;

    @FXML
    TextField mapNameField;

    public MenuController(){

    }

    private File choosenFile;

    @FXML
    public void initialize(){

    }

    @FXML
    public void newMap(){
        String path = getClass().getResource("/resources/maps").getPath() + "/" + mapNameField.getText() + ".map";
        String name = mapNameField.getText();
        File newFile = new File(path);

        if(newFile.exists()){
            newFile.delete();
        }
        try {
            newFile.createNewFile();
            FileWriter writer = new FileWriter(newFile);
            writer.write(name + "\n");
            writer.write("0 0 0");
            writer.close();
        }catch(Exception e) {
            System.out.println("Failed creating file for map" + e);
        }
        RefreshMaps();
    }

    @FXML
    public void deleteMap(){
        if(choosenFile == null)
            return;
        choosenFile.delete();
        choosenFile = null;
        RefreshMaps();
    }

    @FXML
    public void loadMap(){
        if(choosenFile == null)
            return;
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(this.getClass().getResource("/resources/fxml/MainSceneWindow.fxml"));

            StackPane stackPane = loader.load();

            Main.controller = loader.getController();
            Main.controller.Init(choosenFile);
            Scene scene = new Scene(stackPane, 1000, 750);

            Main.primaryStage.setScene(scene);

            KeyPolling.pollScene(scene);
            Main.menuController = null;
        }catch(IOException e){
            System.out.println("We have bug here, failed to open map" + e);
        }
    }


    public void chooseMap(File file){
        choosenFile = file;
    }

    public void Init(){
        RefreshMaps();
    }

    public void Update(float deltaTime){

    }

    //TODO: Create mini image after choosing map

    private void RefreshMaps(){
        File directory = null;
        mapBox.getChildren().clear();
        try {
            directory = new File(getClass().getResource("/resources/maps").getPath());
        }catch(NullPointerException e){
            System.out.println("Cannot read directory with maps, it's probably empty");
            return;
        }
        if(!directory.isDirectory()){
            System.out.println("Cannot read directory with maps, given path probably lead to file");
            return;
        }
        File [] files = directory.listFiles();
        for(File file: files) {
            if(!file.getName().endsWith(".map"))
                continue;
            try{
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String name = bufferedReader.readLine();

                Button button = new Button();
                button.setText(name);
                button.setOnAction(e -> {
                    chooseMap(file);
                });

                mapBox.getChildren().add(button);
                reader.close();
                bufferedReader.close();
            }catch(Exception e){
                System.out.println("Problem with reading file");
            }
        }
    }
}
