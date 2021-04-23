package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import sample.Layer;
import sample.Palette;
import sample.Tile;
import sample.TileSet;

import java.util.ArrayList;

public class MainSceneController {

    @FXML
    Button resetButton;

    @FXML
    ScrollPane assetsBox;

    @FXML
    public Canvas canvas;

    @FXML
    Canvas canvas2;

    @FXML
    StackPane mainPane;

    GraphicsContext gc;

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
        gc = canvas2.getGraphicsContext2D();
        gc.setFill(new Color(0.1,0.1,0.1,0.2));
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
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

        Button returnButton = new Button();
        returnButton.setText("Wróć");
        returnButton.setPrefWidth(70);
        returnButton.setPrefHeight(50);
        returnButton.setMaxWidth(70);
        returnButton.setMaxHeight(50);
        returnButton.setTranslateX(135);
        returnButton.setTranslateY(-350);
        returnButton.setOnAction(e -> {Palette.Init();
                                        mainPane.getChildren().remove(returnButton);});
        mainPane.getChildren().add(returnButton);

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

        tilePane.setOpacity(0.5);

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

    public void clearCanvas(){
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    public void setCanvas(Tile tile, float a, float b){
        gc = canvas.getGraphicsContext2D();
        Image img = tile.getTileSet().getImage();
        int size = tile.getTileSet().getTileSize();
        gc.drawImage(img, tile.getX() * size,tile.getY()*size,
                size,size,a,b,32,32);
    }

    public void LoadLayers(ArrayList<Layer> layers){

    }

}
