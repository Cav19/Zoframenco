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

    /**
     * Creates each of the 18 parking lot locations and places them inside a HashMap for quick access.
     */
    private void createLocations(){
        locations = new HashMap<Integer, Location>();
        locations.put(0, new Location(75, 965, 0));
        locations.put(1, new Location(235, 1085, 1));
        locations.put(2, new Location(515, 965, 2));
        locations.put(3, new Location(915, 1085, 3));
        locations.put(4, new Location(165, 765, 4));
        locations.put(5, new Location(475, 885, 5));
        locations.put(6, new Location(715, 765, 6));
        locations.put(7, new Location(115, 685, 7));
        locations.put(8, new Location(275, 565, 8));
        locations.put(9, new Location(715, 685, 9));
        locations.put(10, new Location(915, 565, 10));
        locations.put(11, new Location(375, 365, 11));
        locations.put(12, new Location(635, 485, 12));
        locations.put(13, new Location(915, 365, 13));
        locations.put(14, new Location(115, 165, 14));
        locations.put(15, new Location(315, 165, 15));
        locations.put(16, new Location(515, 285, 16));
        locations.put(17, new Location(635, 165, 17));
    }
}
