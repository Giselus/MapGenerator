package sample;

import javafx.util.Pair;

import java.util.HashMap;

public class Layer {

    private Tile[][] tiles;
    private boolean [][] blocked;
    private boolean drawable = true;
    public HashMap<Pair<Integer,Integer>, String> events;
    Layer(){
        tiles = new Tile[1001][1001];
        blocked = new boolean[1001][1001];
        events = new HashMap<>();
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

    public void addEvent(int x, int y, String code){
        events.put(new Pair<>(x,y),code);
    }

    public String getEvent(int x, int y){
        return events.get(new Pair<>(x,y));
    }

    public void deleteEvent(int x, int y){
        events.remove(new Pair<>(x,y));
    }
}
