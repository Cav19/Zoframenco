package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;

/**
 * Created by Connor Valenti on 3/29/2016.
 */
public class Passenger {

    private Sprite sprite;

    private int fare;
    private Location origin;
    private Location destination;
    private String type;


    public Passenger(){
        this.origin = setOrigin(MyGdxGame.locations);
        this.destination = setDestination(MyGdxGame.locations);
        generateType();
        sprite.setSize(75,75);
        sprite.setRegionWidth(75);
        sprite.setRegionHeight(75);
        sprite.setX(origin.getX() - 30);
        sprite.setY(origin.getY() - 33);
    }

    private void generateType(){
        switch(MathUtils.random(1, 10)){
            case 1:case 2:case 3:case 4:case 5:case 6:case 7:
                type = "Normal";
                fare = setFare(12);
                sprite = new Sprite(new Texture("images/stick_figure.png"));
                break;
            case 8:case 9:
                type = "Poor";
                fare = setFare(15);
                sprite = new Sprite(new Texture("images/poor_person.png"));
                break;
            case 10:
                type = "Richie";
                fare = setFare(7);
                sprite = new Sprite(new Texture("images/richie.png"));
                break;
        }
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

    public Location getDestination(){
        return this.destination;
    }

    /**
     * Randomly sets the destination for the passenger.
     * @param locations A HashMap of all possible locations for the destination to be.
     * @return The location of the passenger's destination.
     */
    private Location setDestination(HashMap<Integer, Location> locations){
        Location destination;
        do{
            int num = (int)(Math.random() * 18);
            destination = locations.get(num);
        } while(destination.isFull() && destination.equals(origin)); // Ensures that the destination does not already have a passenger at it. Also ensures the destination is not the same as the origin.
        return destination;
    }

    /**
     * Calculates the distance between the passenger's location and their destination. This is also used to calculate the passenger's fare.
     * @param origin The passenger's origin.
     * @param destination The passenger's destination.
     * @return The distance between the passenger's origin and destination.
     */
    private double getTravelDistance(Location origin, Location destination){
        float xDist = Math.abs(origin.getX() - destination.getX());
        float yDist = Math.abs(origin.getY() - destination.getY());
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    private int setFare(int modifier){
        return (int)(getTravelDistance(origin, destination) / modifier);
    }

    public int getFare(){
        return fare;
    }

    public Location getOrigin(){
        return origin;
    }

}
