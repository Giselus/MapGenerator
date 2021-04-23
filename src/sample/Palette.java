package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.security.Key;
import java.util.concurrent.RecursiveAction;

public class Palette {

    private static Tile selectedTile;
    private static Layer activeLayer;
    private static Map currentMap;

    public static void Init(){
        Main.controller.setToTileSetMode();
        for(TileSet tileSet: TileDatabase.tileSets){
            ImageView view = new ImageView();
            Image img = tileSet.getImage();
            view.setImage(img);
            view.setViewport(new Rectangle2D(0,0,Math.min(256,img.getWidth()),Math.min(256,img.getHeight())));
            Main.controller.AddTileSet(view, tileSet);
        }
    }

    public static void chooseTileSet(TileSet tileSet){
        int size = tileSet.getTileSize();
        Main.controller.setToTileMode(tileSet.getTilesInRow(),tileSet.getTilesInColumn(), size);

        Main.controller.setImage(tileSet.getImage());

        for(int i = 0; i < tileSet.getTilesInColumn();i++){
            for(int j = 0; j < tileSet.getTilesInRow();j++){
                Main.controller.AddTile(tileSet.getTile(j,i));
            }
        }
        System.out.println(tileSet.getTilesInColumn() * tileSet.getTilesInRow());
    }

    public static void chooseTile(Tile tile){
        selectedTile = tile;
    }

    public static Tile getSelectedTile(){
        return selectedTile;
    }

    public static void setActiveLayer(Layer layer){
        activeLayer = layer;
    }

    public static void setCurrentMap(Map map){
        currentMap = map;
    }

    public static Map getCurrentMap(){
        return currentMap;
    }

    private static double lastMouseX;
    private static double lastMouseY;

    private static boolean lastTickPressed;

    public static void Update(){
        if(KeyPolling.isDown(MouseButton.PRIMARY)){
            if(lastTickPressed == false){
                lastMouseX = KeyPolling.getMouseX();
                lastMouseY = KeyPolling.getMouseY();
            }
            Draw(lastMouseX,lastMouseY,KeyPolling.getMouseX(),KeyPolling.getMouseY());
            lastTickPressed = true;
        }else{
            lastTickPressed = false;
        }
        lastMouseX = KeyPolling.getMouseX();
        lastMouseY = KeyPolling.getMouseY();
    }

    private static void Draw(double sX, double sY, double dX, double dY){
        if(activeLayer == null)
            return;
        Canvas canvas = Main.controller.getCanvas();
        Map.Camera camera = currentMap.getCamera();
        sX -= canvas.getLayoutX();
        dX -= canvas.getLayoutX();
        sY -= canvas.getLayoutY();
        dY -= canvas.getLayoutY();
        Line line = new Line(sX,sY,dX,dY);
        if(sX > dX){
            double temp = sX;
            sX = dX;
            dX = temp;
            temp = sY;
            sY = dY;
            dY = temp;
        }
        for(int x = (int)camera.getX() / 32;x <= (int)(camera.getX()+camera.getWidth())/32;x++) {
            for(int y = (int)camera.getY()/ 32;y <= (int)(camera.getY()+camera.getHeight())/32;y++){
                double left = Math.max((x*32) - camera.getX(), 0);
                double right = Math.min((x*32) + 32 - camera.getX(), canvas.getWidth());
                double up = Math.max((y*32) + 32 - camera.getY(),0);
                double down = Math.min((y*32) - camera.getY(),canvas.getHeight());
                Shape shape1 = (Shape)line;
                Rectangle rect = new Rectangle(left,down,right-left,up-down);
                if(Shape.intersect(shape1,rect).getBoundsInLocal().getWidth() != -1){
                    activeLayer.setTileAtPos(x,y,selectedTile);
                }

            }
        }
    }



}
