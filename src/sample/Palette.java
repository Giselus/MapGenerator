package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Palette {

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

    public static void chooseTile(Tile tile){//TODO: Change brush

    }
}
