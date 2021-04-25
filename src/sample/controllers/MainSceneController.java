package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import sample.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainSceneController {

    @FXML
    ScrollPane assetsBox;

    @FXML
    public Canvas canvas;

    @FXML
    Canvas canvas2;

    @FXML
    StackPane mainPane;

    @FXML
    TilePane layersPane;


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

    @FXML
    public void deleteLayer(){
        Palette.getCurrentMap().deleteLayer();
    }

    @FXML
    public void setCollisionMode(ActionEvent e){
        Palette.setCollisionMode(((CheckBox)e.getTarget()).isSelected());
    }

    @FXML
    public void saveAndClose(){
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(this.getClass().getResource("/resources/fxml/Menu.fxml"));

            StackPane stackPane = loader.load();

            Main.menuController = loader.getController();
            Main.menuController.Init();
            Scene scene = new Scene(stackPane, 1000, 750);

            Main.primaryStage.setScene(scene);

            KeyPolling.pollScene(scene);
            Main.controller = null;

            Palette.getCurrentMap().saveToFile();

        }catch(IOException e){
            System.out.println("We have bug here, failed to open menu" + e);
        }
    }

    public void Init(File mapSource){
        Palette.Init(mapSource);
    }

    public void Update(float deltaTime){
        Palette.Update(deltaTime);
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
        returnButton.setOnAction(e -> {Palette.createTileSetsView();
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

    public void setCanvas(Tile tile, int a, int b){
        gc = canvas.getGraphicsContext2D();
        Image img = tile.getTileSet().getImage();
        int size = tile.getTileSet().getTileSize();
        gc.drawImage(img, tile.getX() * size,tile.getY()*size,
                size,size,a,b,32,32);
    }

    public void drawCross(int x, int y){
        gc = canvas.getGraphicsContext2D();
        Image img = new Image(getClass().getResource("/resources/textures/mark.png").toString());
        int size = 32;
        gc.drawImage(img,x,y,size,size);
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public void RefreshLayersGUI(ArrayList<Layer> layers){
        layersPane.getChildren().clear();
        Integer id = 0;
        createAddLayerButton(id);
        for(Layer layer: layers){
            AnchorPane pane = new AnchorPane();
            pane.setPrefWidth(95);
            pane.setPrefHeight(30);
            Button layerButton = new Button();
            pane.getChildren().add(layerButton);

            layerButton.setOnAction(e -> {
                Palette.getCurrentMap().chooseLayer(layer);
            });
            layerButton.setText("Warstwa: " + (++id).toString());
            layerButton.setPrefSize(80,30);

            CheckBox visibleCheck = new CheckBox();
            pane.getChildren().add(visibleCheck);
            visibleCheck.setPrefSize(15,15);
            visibleCheck.setSelected(layer.isDrawable());
            visibleCheck.setOnAction(e -> {
                layer.setDrawable(visibleCheck.isSelected());
            });
            visibleCheck.setLayoutX(80);
            visibleCheck.setLayoutY(6.5);

            layersPane.getChildren().add(pane);

            createAddLayerButton(id);
        }
    }

    private void createAddLayerButton(int id){
        Button button = new Button();
        button.setText("Dodaj");
        button.setPrefSize(50,50);
        button.setOnAction(e -> {
            Palette.getCurrentMap().addLayer(id);
        });
        layersPane.getChildren().add(button);
    }
}
