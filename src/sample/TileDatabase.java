package sample;

import java.util.*;
import java.util.Map;

public class TileDatabase {

    public static ArrayList<TileSet> tileSets = new ArrayList<>();
    public static Map<Integer, Tile> tiles;
    public static Tile getTile(int id){
        return tiles.get(id);
    }

    public static void Init(){
        tiles = new HashMap<Integer, Tile>();
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/BaseChip_pipo.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/Dirt_pipo.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/Flower_pipo.png").toString()));

    }

}
