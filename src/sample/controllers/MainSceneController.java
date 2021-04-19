package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import sample.Palette;
import sample.Tile;
import sample.TileSet;

public class MainSceneController {

    @FXML
    Button resetButton;

    @FXML
    ScrollPane assetsBox;


    VBox vBox;


    StackPane stackPane;
    TilePane tilePane;
    ImageView tileSetImg;

    private int tileSize;
    private int xSize;
    private int ySize;

    public MainSceneController(){

    }

    @FXML
    public void initialize(){

    }

    public void AddTileSet(ImageView view, TileSet tileSet){
        view.setOnMouseClicked(
            e ->{
                Palette.chooseTileSet(tileSet);
            }
        );
        vBox.getChildren().add(view);
    }

    public void AddTile(Tile tile){
        Button button = new Button();
        button.setPrefWidth(tileSize);
        button.setPrefHeight(tileSize);
        button.setOnMouseClicked(
            e ->{
                Palette.chooseTile(tile);
            }
        );
        tilePane.getChildren().add(button);
    }

    public void setToTileSetMode(){
        if(tilePane != null)
            tilePane.getChildren().clear();
        vBox = new VBox();
        vBox.setSpacing(50);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(50,0,0,0));
        vBox.setPrefWidth(328);
        assetsBox.setContent(vBox);

    }

    public void setImage(Image img){
        tileSetImg.setImage(img);
        tileSetImg.setFitHeight(ySize * tileSize);
        tileSetImg.setFitWidth(xSize * tileSize);
    }

    public void setToTileMode(int sizeX, int sizeY, int tileSize){
        if(vBox != null)
            vBox.getChildren().clear();

        this.tileSize = tileSize;
        this.xSize = xSize;
        this.ySize = ySize;

        StackPane stackPane = new StackPane();
        tileSetImg = new ImageView();

        tilePane = new TilePane();

        stackPane.getChildren().add(tileSetImg);
        stackPane.getChildren().add(tilePane);

        tilePane.setAlignment(Pos.TOP_LEFT);
        stackPane.setAlignment(Pos.TOP_LEFT);

        tilePane.setTileAlignment(Pos.CENTER);

        tilePane.setOpacity(0);

        stackPane.setPadding(new Insets(50,0,0,(328 - tileSize*sizeX)/2));
        tilePane.setPrefWidth(tileSize * sizeX + (328 - tileSize * sizeX)/2);
        stackPane.setPrefWidth(tileSize * sizeX + (328 - tileSize * sizeX)/2);
        tileSetImg.setFitWidth(sizeX*tileSize);
        tileSetImg.setFitHeight(sizeY*tileSize);

        tilePane.setPrefTileWidth(tileSize);
        tilePane.setPrefTileHeight(tileSize);

        tilePane.setPrefColumns(sizeX);
        tilePane.setPrefRows(sizeY);

        assetsBox.setContent(stackPane);
    }



}
