package com.mygdx.game;

import com.badlogic.gdx.Game;

import java.util.HashMap;


public class MyGdxGame extends Game {
    public static HashMap<Integer, Location> locations;

    @Override
    public void create () {
        createLocations();
        setScreen(new HomeScreen(this));
    }

    @Override
    public void render () {
        super.render();

    }

    private void createLocations(){
        locations = new HashMap<Integer, Location>();
        locations.put(0, new Location(60, 940, 0));
        locations.put(1, new Location(200, 1040, 1));
        locations.put(2, new Location(500, 940, 2));
        locations.put(3, new Location(900, 1040, 3));
        locations.put(4, new Location(150, 740, 4));
        locations.put(5, new Location(450, 870, 5));
        locations.put(6, new Location(700, 740, 6));
        locations.put(7, new Location(95, 670, 7));
        locations.put(8, new Location(260, 540, 8));
        locations.put(9, new Location(700, 670, 9));
        locations.put(10, new Location(900, 540, 10));
        locations.put(11, new Location(350, 340, 11));
        locations.put(12, new Location(610, 470, 12));
        locations.put(13, new Location(900, 340, 13));
        locations.put(14, new Location(95, 140, 14));
        locations.put(15, new Location(300, 140, 15));
        locations.put(16, new Location(500, 270, 16));
        locations.put(17, new Location(610, 140, 17));
    }
}
