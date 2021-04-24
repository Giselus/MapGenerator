package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.File;

public class MenuController {

    @FXML
    VBox mapBox;

    public MenuController(){

    }

    @FXML
    public void initialize(){

    }

    @FXML
    public void deleteMap(){//TODO

    }

    @FXML
    public void loadMap(){//TODO

    }

    //TODO: Adding new maps

    public void Init(){//TODO
        RefreshMaps();
    }

    public void Update(float deltaTime){//TODO

    }

    private void RefreshMaps(){
        File directory = null;
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
            System.out.println("found map");
            //TODO: Create view for every existing map
        }
    }
}
