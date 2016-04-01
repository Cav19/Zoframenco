package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Valenti on 3/29/2016.
 */
public class Location {

    private String name;
    private boolean isFull;
    private int id;

    private float x;
    private float y;
    private Rectangle rectangle;

    public Location(int id){
        //this.name = name;
        this.id = id;
        this.isFull = false;
    }

    public Location(String name, float x, float y, Rectangle rectangle){
        this.name = name;
        this.isFull = false;
        this.x = x;
        this.y = y;
        this.rectangle = rectangle;
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
