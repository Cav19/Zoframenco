package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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


    /**
     * Creates a new instance of a passenger with a set origin and destination.
     * @param locations A HashMap of all possible locations for the passenger to be placed at.
     */
    public Passenger(HashMap<Integer, Location> locations){
        this.origin = setOrigin(locations);
        this.destination = setDestination(locations);
        this.fare = (int)(getTravelDistance(origin, destination) / 12);
        this.timer = (int)(getTravelDistance(origin, destination) / 4 + 15);
        this.getSprite().setSize(60,65);
        sprite.setRegionWidth(75);
        sprite.setRegionHeight(75);
        sprite.setX(origin.getX() - 30);
        sprite.setY(origin.getY() - 33);
    }

    public Sprite getSprite(){
        return sprite;
    }

    /**
     * Randomly sets the origin for the passenger.
     * @param locations A HashMap of all possible locations for the origin to be.
     * @return The location of the passenger's origin.
     */
    private Location setOrigin(HashMap<Integer, Location> locations) {
        Location origin;
        do {
            int num = (int) (Math.random() * 18);
            origin = locations.get(num);
        } while (origin.isFull()); // Ensures that the passenger won't be placed at a location that already has a passenger.
        origin.addPassenger();
        return origin;
    }

    public  Location getDestination(){
        return this.destination;
    }

    /**
     * Randomly sets the destination for the passenger.
     * @param locations A HashMap of all possible locations for the destination to be.
     * @return The location of the passenger's destination.
     */
    public Location setDestination(HashMap<Integer, Location> locations){
        Location destination;
        do{
            int num = (int)(Math.random() * 18);
            destination = locations.get(num);
        } while(destination.isFull() && destination.equals(origin)); // Ensures that the destination does not already have a passenger at it. Also ensures the destination is not the same as the origin.
        return destination;
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

    /**
     * Calculates the distance between the passenger's location and their destination. This is also used to calculate the passenger's fare.
     * @param origin The passenger's origin.
     * @param destination The passenger's destination.
     * @return The distance between the passenger's origin and destination.
     */
    public double getTravelDistance(Location origin, Location destination){
        float xDist = Math.abs(origin.getX() - destination.getX());
        float yDist = Math.abs(origin.getY() - destination.getY());
        return Math.sqrt(xDist * xDist + yDist * yDist);
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
