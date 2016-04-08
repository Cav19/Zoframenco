package com.mygdx.game.objects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Connor Valenti on 3/29/2016.
 */
public class Location {

    private String name;
    private boolean isFull;

    private int x;
    private int y;
    private Rectangle rectangle;

    public Location(int x, int y){
        //this.name = name;
        this.isFull = false;
        this.x = x;
        this.y = y;
    }


    public void addPassenger(){
        isFull = true;
    }

    public void removePassenger(){
        isFull = false;
    }

    public String getName(){
        return name;
    }

    public boolean isFull(){
        return isFull;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
