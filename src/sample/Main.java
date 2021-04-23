package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.controllers.MainSceneController;

import java.net.URL;

public class Main extends Application {

    public static MainSceneController controller;
    public static Map map;
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

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                tick();
            }
        };
        timer.start();
    }
    private float lastFrameNanos;

    private void tick(){
        //Input -> logic -> rendering

        map.Draw();

    }

    private void Init(){
        TileDatabase.Init();
        Palette.Init();
        map = new Map();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
