package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileSet {

    private Image source;

    private int tilesInRow;
    private int tilesInColumn;

    private int tileSize = 32;

    private Tile tiles[][];

    public TileSet(String filePath) {
        source = new Image(filePath);
        tilesInRow = (int) source.getWidth() / tileSize;
        tilesInColumn = (int) source.getHeight() / tileSize;

        tiles = new Tile[tilesInRow][tilesInColumn];

        for(int i = 0; i < tilesInColumn;i++){
            for(int j = 0; j < tilesInRow;j++){
                tiles[j][i] = new Tile(this,j,i);
            }
        }

    }

    public Image getImage(){
        return  source;
    }
    public int getTilesInRow(){
        return tilesInRow;
    }
    public int getTilesInColumn(){
        return tilesInColumn;
    }
    public int getTileSize(){
        return tileSize;
    }
    public Tile getTile(int x, int y){
        return tiles[x][y];
    }

}
