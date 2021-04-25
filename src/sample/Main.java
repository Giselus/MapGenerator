package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.controllers.MainSceneController;
import sample.controllers.MenuController;

import java.net.URL;

public class Main extends Application {

    public static MainSceneController controller;
    public static MenuController menuController;

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(this.getClass().getResource("/resources/fxml/Menu.fxml"));
        StackPane stackPane = loader.load();

        menuController = loader.getController();
        menuController.Init();
        Scene scene = new Scene(stackPane,1000,750);


        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Hello World");
        primaryStage.show();

        KeyPolling.pollScene(scene);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                float deltaTime = (float)((now - lastFrameNanos) /1e9);
                lastFrameNanos = now;
                tick(deltaTime);
            }
        };
        timer.start();

        TileDatabase.Init();
    }
    private float lastFrameNanos;

    private void tick(float deltaTime){
        //Input -> logic -> rendering
        if(menuController != null){
            menuController.Update(deltaTime);
        }else{
            controller.Update(deltaTime);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
