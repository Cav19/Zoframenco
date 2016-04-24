package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

/**
 * Created by Connor Valenti on 3/29/2016.
 */
public class Passenger {

    private Texture texture= new Texture("stick_figure.png");
    private Sprite sprite= new Sprite(texture);

    private int fare;
    private int timer;
    private Location origin;
    private Location destination;
    public MyGdxGame game;


    public Passenger(HashMap<Integer, Location> locations){
        this.origin = setOrigin(locations);
        this.destination = setDestination(locations);
        this.fare = (int)(getTravelDistance(origin, destination) / 12);
        this.timer = (int)(getTravelDistance(origin, destination) / 4 + 15);
        this.getSprite().setSize(60,65);
        sprite.setRegionWidth(75);
        sprite.setRegionHeight(75);
        sprite.setX(origin.getX());
        sprite.setY(origin.getY());
    }

    public Texture getTexture(){
        return texture;
    }

    public Sprite getSprite(){
        return sprite;
    }

    private Location setOrigin(HashMap<Integer, Location> locations) {
        Location origin;
        do {
            int num = (int) (Math.random() * 18);
            origin = locations.get(num);
        } while (origin.isFull());
        return origin;
    }

    public  Location getDestination(){
        return this.destination;
    }

    public Location setDestination(HashMap<Integer, Location> locations){
        Location destination;
        do{
            int num = (int)(Math.random() * 18);
            destination = locations.get(num);
        } while(destination.isFull() && destination.equals(origin));
        return destination;
    }

    public void drawPassenger(){
        SpriteBatch batch= game.batch;
        batch.begin();
        this.getSprite().draw(batch);
        batch.end();
    }

    public void enterTaxi(){
        this.getSprite().setAlpha(0);
    }

    public void exitTaxi(){
        //not quite right graphically, trying to show passenger leaving the cab
        this.origin=this.destination;
        this.getSprite().setPosition(origin.getX(), origin.getY());
        this.getSprite().setAlpha(1);
    }


    public double getTravelDistance(Location location, Location destination){
        float xDist = Math.abs(location.getX() - destination.getX());
        float yDist = Math.abs(location.getY() - destination.getY());
        return Math.sqrt(xDist * xDist + yDist * yDist);
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

    public Location getOrigin(){
        return origin;
    }

}
