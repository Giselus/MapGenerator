package sample;

public class Layer {

    private Tile[][] tiles;
    private boolean [][] blocked;
    private boolean drawable = true;
    Layer(){
        tiles = new Tile[1001][1001];
        blocked = new boolean[1001][1001];
    }

    public Tile getTileAtPos(int x, int y){
        return tiles[x][y];
    }

    public void setTileAtPos(int x, int y, Tile tile){
        tiles[x][y] = tile;
    }

    public void setCollisionAtPos(int x, int y, boolean coll){
        blocked[x][y] = coll;
    }

    public boolean getCollisionAtPos(int x, int y){
        return blocked[x][y];
    }

    public boolean isDrawable(){
        return drawable;
    }

    public void setDrawable(boolean state){
        drawable = state;
    }
}
