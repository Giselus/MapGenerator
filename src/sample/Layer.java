package sample;

public class Layer {

    private Tile[][] tiles;
    private boolean drawable = true;
    Layer(){
        tiles = new Tile[1001][1001];
    }

    public Tile getTileAtPos(int x, int y){
        return tiles[x][y];
    }

    public void setTileAtPos(int x, int y, Tile tile){
        tiles[x][y] = tile;
    }

    public boolean isDrawable(){
        return drawable;
    }

    public void setDrawable(boolean state){
        drawable = state;
    }
}
