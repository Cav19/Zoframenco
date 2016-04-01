package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

/**
 * Created by Valenti on 3/29/2016.
 */
public class Passenger {

    private Texture texture;
    private Sprite sprite;

    private int fare;
    private int timer;
    private Location location;
    private Location destination;


    public Passenger(Texture texture){
        this.texture = texture;
        this.sprite = new Sprite(texture);
    }

    public Texture getTexture(){
        return texture;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setLocation(HashMap<Integer, Location> locations){
        int num = (int)(Math.random() * 18);
        do{
            this.location = locations.get(num);
        } while(location.isFull());
        location.addPassenger();
    }

    public void setDestination(HashMap<Integer, Location> locations){
        int num = (int)(Math.random() * 18);
        do{
            this.destination = locations.get(num);
        } while(!destination.getName().equals(location.getName()));
    }

    public double getTravelDistance(Location location, Location destination){
        float xDist = Math.abs(location.getX() - destination.getX());
        float yDist = Math.abs(location.getY() - destination.getY());
        double distance = Math.sqrt(xDist * xDist + yDist * yDist);

        return distance;
    }

}
