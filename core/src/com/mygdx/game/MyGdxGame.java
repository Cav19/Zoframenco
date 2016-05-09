package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.HashMap;

public  class MyGdxGame extends Game {
    public static HashMap<Integer, Location> locations;

    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 1150;

    public static int score = 0;
    public static int worldTimer = 90;

    public static Screen HomeScreen;
    public static Screen PlayScreen ;
    public static Screen EndScreen ;
    public static Screen InstructionScreen;

    @Override
    public void create() {
        createLocations();
          HomeScreen = new HomeScreen(this);

         setScreen(HomeScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    /**
     * Creates each of the 18 parking lot locations and places them inside a HashMap for quick access.
     */
    private void createLocations() {
        locations = new HashMap<Integer, Location>();
        locations.put(0, new Location(75, 965, 0, "the park"));
        locations.put(1, new Location(235, 1085, 1, "Connor's house"));
        locations.put(2, new Location(515, 965, 2, "Paul's house"));
        locations.put(3, new Location(915, 1085, 3, "the AMC Movie Theater"));
        locations.put(4, new Location(165, 765, 4, "Macalester"));
        locations.put(5, new Location(475, 885, 5, "the police station"));
        locations.put(6, new Location(715, 765, 6, "the club"));
        locations.put(7, new Location(115, 685, 7, "Shish"));
        locations.put(8, new Location(275, 565, 8, "the church"));
        locations.put(9, new Location(715, 685, 9, "the bar"));
        locations.put(10, new Location(915, 565, 10, "Rosedale Mall"));
        locations.put(11, new Location(375, 365, 11, "Pizza Luce"));
        locations.put(12, new Location(635, 485, 12, "Franceso's house"));
        locations.put(13, new Location(915, 365, 13, "Mengdie's house"));
        locations.put(14, new Location(115, 165, 14, "the airport"));
        locations.put(15, new Location(315, 165, 15, "Zora's house"));
        locations.put(16, new Location(515, 285, 16, "the hospital"));
        locations.put(17, new Location(635, 165, 17, "the gas station"));
    }

    public void addScore(int value) {
        score += value;
    }
}
