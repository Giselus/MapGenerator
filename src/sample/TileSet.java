package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileSet {

    private Image source;

    private ImageView view;

    private ImageView[] tiles;

    private int tilesInRow;
    private int tilesInColumn;

    private int tileSize = 32;

    public TileSet(String filePath) {
        source = new Image(filePath);
        tilesInRow = (int) source.getWidth() / tileSize;
        tilesInColumn = (int) source.getHeight() / tileSize;
    }

    public Image getImage(){
        return  source;
    }

}
