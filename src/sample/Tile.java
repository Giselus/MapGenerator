package sample;

public class Tile {

    private TileSet tileSet;
    private int xPos;
    private int yPos;
    private int id;

    private static int counter = 0;

    public Tile(TileSet tileSet, int xPos, int yPos){
        this.tileSet = tileSet;
        this.xPos = xPos;
        this.yPos = yPos;
        counter++;
        this.id = counter;
        TileDatabase.tiles.put(this.id,this);
    }

    public TileSet getTileSet(){
        return tileSet;
    }

    public int getX(){
        return xPos;
    }

    public int getY(){
        return yPos;
    }

    public int getId(){
        return id;
    }

}
