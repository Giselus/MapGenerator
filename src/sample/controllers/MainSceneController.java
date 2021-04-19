package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MainSceneController {

    @FXML
    Button resetButton;

    @FXML
    VBox assetsBox;

    public MainSceneController(){

    }

    @FXML
    public void initialize(){

    }

    public void AddImage(Image img){
        ImageView view = new ImageView();
        view.setImage(img);
        view.setViewport(new Rectangle2D(0,0,Math.min(256,img.getWidth()),Math.min(256,img.getHeight())));
        assetsBox.getChildren().add(view);
    }

}
