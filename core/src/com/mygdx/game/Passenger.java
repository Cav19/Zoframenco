package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

/**
 * Created by Connor Valenti on 3/29/2016.
 */
public class Passenger {

    public Texture texture;
    private Sprite sprite;

    private int fare;
    private int timer;
    private Location origin;
    private Location destination;
    public MyGdxGame game;


    public Passenger(HashMap<Integer, Location> locations){
        this.texture = new Texture("stick_figure.png");
        this.sprite = new Sprite(texture);
        this.origin = setOrigin(locations);
        this.destination = setDestination(locations);
        this.fare = (int)(getTravelDistance(origin, destination) / 2);
        this.timer = (int)(getTravelDistance(origin, destination) / 4 + 15);
        sprite.setRegionWidth(75);
        sprite.setRegionHeight(75);
    }

    public Texture getTexture(){
        return texture;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public Location setOrigin(HashMap<Integer, Location> locations){
        Location origin;
        do{
            int num = (int)(Math.random() * 18);
            origin = locations.get(num);
        } while(origin.isFull());
        sprite.setX(origin.getX());
        sprite.setY(origin.getY());
        origin.addPassenger();
        return origin;
    }

    public Location setDestination(HashMap<Integer, Location> locations){
        Location destination;
        do{
            int num = (int)(Math.random() * 18);
            destination = locations.get(num);
        } while(destination.isFull());
        return destination;
    }

    public void drawPassenger(){
        SpriteBatch batch= game.batch;
        batch.begin();
        this.getSprite().draw(batch);
        batch.end();
    }


    public double getTravelDistance(Location location, Location destination){
        float xDist = Math.abs(location.getX() - destination.getX());
        float yDist = Math.abs(location.getY() - destination.getY());
        double distance = Math.sqrt(xDist * xDist + yDist * yDist);

        return distance;
    }

    public void setFare(int fare){
        this.fare = fare;
    }

    public void setTimer(int timer){
        this.timer = timer;
    }

    public int getTimer(){
        return timer;
    }

    public int getFare(){
        return fare;
    }

}
