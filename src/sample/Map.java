package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Map {


    public class Camera{
        private double x,y;
        private double width;
        private double height;
        private double minX = 0;
        private double minY = 0;
        private double maxX = 32000;
        private double maxY = 32000;
        private float speed = 90;
        Camera(Canvas can){
            width = can.getWidth();
            height = can.getHeight();
            x = maxX/2 - width/2;
            y = maxY/2 - height/2;
        }

        public void addPosition(double x, double y){
            setPosition(this.x + x, this.y + y);
        }

        public void setPosition(double x, double y){
            this.x = x;
            this.y = y;
            if(this.x < minX)
                this.x = minX;
            if(this.y < minY)
                this.y = minY;
            if(this.x > maxX - width)
                this.x = maxX - width;
            if(this.y > maxY - height)
                this.y = maxY - height;
        }
        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public double getWidth(){
            return width;
        }
        public double getHeight(){
            return height;
        }
    }

    private ArrayList<Layer> layers;
    private Camera camera;
    private Layer selectedLayer;
    private File source;
    private String name;

    public Map(File source){
        this.source = source;
        layers = new ArrayList<>();
        camera = new Camera(Main.controller.canvas);
        RefreshLayersGUI();
        loadFromFile();
    }

    private void loadFromFile(){
        try{
            layers.clear();
            Scanner scanner = new Scanner(source);
            name = scanner.nextLine();
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            int layersNo = scanner.nextInt();
            int xOffset = (1001 - width)/2;
            int yOffset = (1001 - height)/2;
            for(int i = 0; i < layersNo;i++){
                Layer layer = new Layer();

                for(int y = 0; y < height;y++){
                    for(int x = 0; x < width;x++){
                        int tileId = scanner.nextInt();
                        if(tileId == 0)
                            continue;
                        layer.setTileAtPos(xOffset+x,yOffset+y,TileDatabase.getTile(tileId));
                    }
                }
                for(int y = 0; y < height;y++){
                    for(int x = 0; x < width;x++){
                        int blocked = scanner.nextInt();
                        if(blocked == 0)
                            continue;
                        layer.setCollisionAtPos(xOffset + x,yOffset + y,true);
                    }
                }
                int eventsNo = scanner.nextInt();
                for(int j = 1; j <= eventsNo;j++){
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    String code = scanner.next();
                    layer.addEvent(xOffset + x,yOffset + y,code);
                }

                layers.add(layer);
            }
            scanner.close();
            RefreshLayersGUI();

        }catch(Exception e){
            System.out.println("Failed to load file: " + e);
        }
    }

    public void saveToFile(){
        int xMin = 2000;
        int xMax = -1;
        int yMin = 2000;
        int yMax = -1;
        for(Layer layer: layers){
            for(int y = 0; y < 1001;y++){
                for(int x = 0; x < 1001;x++){
                    if(layer.getTileAtPos(x,y) != null || layer.getCollisionAtPos(x,y) || layer.getEvent(x,y) != null){
                        xMin = Math.min(xMin,x);
                        xMax = Math.max(xMax,x);
                        yMin = Math.min(yMin,y);
                        yMax = Math.max(yMax,y);
                    }
                }
            }
        }
        int width = xMax-xMin + 1;
        int height = yMax-yMin + 1;
        if(width < 0)
            width = 0;
        if(height < 0)
            height = 0;
        try {
            FileWriter writer = new FileWriter(source);
            writer.write(name + "\n");
            writer.write(String.valueOf(width) + " ");
            writer.write(String.valueOf(height) + " ");
            writer.write(String.valueOf(layers.size()) + "\n");

            for(Layer layer: layers){
                for(int y = yMin; y <= yMax;y++){
                    for(int x = xMin; x <= xMax;x++){
                        if(layer.getTileAtPos(x,y) != null){
                            writer.write(String.valueOf(layer.getTileAtPos(x,y).getId()) + " ");
                        }else{
                            writer.write("0 ");
                        }
                    }
                    writer.write("\n");
                }

                for(int y = yMin; y <= yMax;y++){
                    for(int x = xMin; x <= xMax;x++){
                        if(layer.getCollisionAtPos(x,y) == true){
                            writer.write("1 ");
                        }else{
                            writer.write("0 ");
                        }
                    }
                    writer.write("\n");
                }
                writer.write(String.valueOf(layer.events.size()) + "\n");
                for(java.util.Map.Entry entry: layer.events.entrySet()){
                    writer.write(String.valueOf((((Pair<Integer,Integer>)(entry.getKey())).getKey() - xMin)) + " ");
                    writer.write(String.valueOf((((Pair<Integer,Integer>)(entry.getKey())).getValue() - yMin)) + " ");
                    writer.write((String)entry.getValue() + "\n");

                }
            }
            writer.close();
        }catch(Exception e){
            System.out.println("Failed to write to file: " + e);
        }
    }

    public Camera getCamera(){
        return camera;
    }

    public void chooseLayer(Layer layer){
        Palette.setActiveLayer(layer);
        selectedLayer = layer;
    }

    public void addLayer(int id){
        layers.add(id, new Layer());
        RefreshLayersGUI();
    }

    public void deleteLayer(){
        layers.remove(selectedLayer);
        chooseLayer(null);
        RefreshLayersGUI();
    }

    public void Update(float deltaTime){
        HandleCameraMovement(deltaTime);
        Render();
    }

    private void HandleCameraMovement(float deltaTime){
        float deltaX = 0;
        float deltaY = 0;
        if(KeyPolling.isDown(KeyCode.A)){
            deltaX += -camera.speed * deltaTime;
        }
        if(KeyPolling.isDown(KeyCode.D)){
            deltaX += camera.speed * deltaTime;
        }
        if(KeyPolling.isDown(KeyCode.S)){
            deltaY += camera.speed * deltaTime;
        }
        if(KeyPolling.isDown(KeyCode.W)){
            deltaY += -camera.speed*deltaTime;
        }
        camera.addPosition(deltaX,deltaY);
    }

    private void Render(){
        Main.controller.clearCanvas();
        for(int x = (int)camera.getX() / 32;x <= (int)(camera.getX()+camera.width)/32;x++) {
            for(int y = (int)camera.getY()/ 32;y <= (int)(camera.getY()+camera.height)/32;y++){
                for(Layer layer: layers) {
                    if(!layer.isDrawable())
                        continue;
                    Tile tile = layer.getTileAtPos(x, y);
                    if (tile != null) {
                        int size = tile.getTileSet().getTileSize();

                        Main.controller.setCanvas(tile, (int) ((x * 32) - camera.getX()), (int) ((y * 32) - camera.getY()));
                    }
                    if(Palette.isCollisionMode()){
                        boolean blocked = layer.getCollisionAtPos(x,y);
                        if(blocked){
                            Main.controller.drawCross((int)(x*32 - camera.getX()),(int)(y*32 - camera.getY()));
                        }
                    }
                    if(Palette.isEventMode()){
                        if(layer.getEvent(x,y) != null){
                            Main.controller.drawCross((int)(x*32 - camera.getX()),(int)(y*32 - camera.getY()));
                        }
                    }
                }
            }
        }
    }

    private void RefreshLayersGUI(){
        Main.controller.RefreshLayersGUI(layers);
    }


}
