package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.controllers.MainSceneController;

import java.net.URL;

public class Main extends Application {

    public static MainSceneController controller;
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(this.getClass().getResource("/resources/fxml/MainSceneWindow.fxml"));
        StackPane stackPane = loader.load();

        controller = loader.getController();

        Scene scene = new Scene(stackPane,1000,750);


        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Hello World");
        primaryStage.show();

        Init();
        
    }

    private void Init(){
        TileDatabase.Init();
        for(TileSet tileSet: TileDatabase.tileSets){
            controller.AddImage(tileSet.getImage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
