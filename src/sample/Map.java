package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.security.Key;
import java.util.ArrayList;

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
            x = maxX/2;
            y = maxY/2;
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
    Map(){
        layers = new ArrayList<>();
        layers.add(new Layer());
        layers.add(new Layer());
        Palette.setActiveLayer(layers.get(0));
        Palette.setCurrentMap(this);
        camera = new Camera(Main.controller.canvas);
        RefreshLayersGUI();
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
                    Tile tile = layer.getTileAtPos(x,y);
                    if(tile == null)
                        continue;
                    int size = tile.getTileSet().getTileSize();

                    Main.controller.setCanvas(tile, (int)((x*32)-camera.getX()), (int)((y*32)-camera.getY()));
                }
            }
        }
    }

    private void RefreshLayersGUI(){
        Main.controller.RefreshLayersGUI(layers);
    }


}
