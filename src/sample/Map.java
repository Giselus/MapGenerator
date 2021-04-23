package sample;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

public class Map {


    private class Camera{
        private double x,y;
        private double width;
        private double height;
        private double minX = 0;
        private double minY = 0;
        private double maxX = 32000;
        private double maxY = 32000;
        Camera(Canvas can){
            width = can.getWidth();
            height = can.getHeight();
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
    }

    ArrayList<Layer> layers;
    Camera camera;
    Map(){
        layers = new ArrayList<>();
        camera = new Camera(Main.controller.canvas);
    }

    public void Draw(){
        Main.controller.clearCanvas();
        for(int x = (int)camera.getX() / 32;x <= (int)(camera.getX()+camera.width)/32;x++) {
            for(int y = (int)camera.getY()/ 32;y <= (int)(camera.getY()+camera.height)/32;y++){
                Tile tile = Palette.getSelectedTile();
                if(tile == null)
                    continue;
                int size = tile.getTileSet().getTileSize();
                Main.controller.setCanvas(tile, (float)((x*32)-camera.getX()), (float)((y*32)-camera.getY()));
            }
        }
    }
}
