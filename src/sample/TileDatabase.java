package sample;

import java.util.ArrayList;

public class TileDatabase {

    public static ArrayList<TileSet> tileSets = new ArrayList<>();


    public static void Init(){

        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/BaseChip_pipo.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/Dirt_pipo.png").toString()));
        tileSets.add(new TileSet(TileDatabase.class.getResource("/resources/textures/Flower_pipo.png").toString()));

    }

}
