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
    private Sprite averagePerson= new Sprite(new Texture("images/stick_figure.png"));
    private Sprite averagePerson2= new Sprite(new Texture("images/stick_figure2.png"));
    private Sprite averagePerson3= new Sprite(new Texture("images/stick_figure3.png"));
    private Sprite poorPerson= new Sprite(new Texture("images/poor_person.png"));
    private Sprite poorPerson2= new Sprite(new Texture("images/poor_person2.png"));
    private Sprite richPerson= new Sprite(new Texture("images/richie.png"));
    private Sprite richPerson2= new Sprite(new Texture("images/richie2.png"));



    public Passenger() {
        this.origin = setOrigin(MyGdxGame.locations);
        this.destination = setDestination(MyGdxGame.locations);
        generateType();
        sprite.setSize(75, 100);
        sprite.setRegionWidth(75);
        sprite.setRegionHeight(100);
        sprite.setX(origin.getX() - 30);
        sprite.setY(origin.getY() - 33);


    }


    private void generateType() {
        switch (MathUtils.random(1, 7)) {
            case 1:
                fare = setFare(12);
                sprite = averagePerson;
                break;
            case 2:
                fare = setFare(12);
                sprite = averagePerson2;
                break;
            case 3:
                fare = setFare(12);
                sprite = averagePerson3;
                break;
            case 4:
                fare = setFare(15);
                sprite = poorPerson;
                break;
            case 5:
                fare = setFare(15);
                sprite = poorPerson2;
                break;
            case 6:
                fare = setFare(7);
                sprite = richPerson;
                break;
            case 7:
                fare = setFare(7);
                sprite = richPerson2;
                break;
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Randomly sets the origin for the passenger.
     *
     * @param locations A HashMap of all possible locations for the origin to be.
     * @return The location of the passenger's origin.
     */
    private Location setOrigin(HashMap<Integer, Location> locations) {
        Location origin;
        do {
            int num = (int) (Math.random() * 18);
            origin = locations.get(num);
        }
        while (origin.isFull()); // Ensures that the passenger won't be placed at a location that already has a passenger.
        origin.addPassenger();
        return origin;
    }

    public Location getDestination() {
        return this.destination;
    }

    /**
     * Randomly sets the destination for the passenger.
     *
     * @param locations A HashMap of all possible locations for the destination to be.
     * @return The location of the passenger's destination.
     */
    private Location setDestination(HashMap<Integer, Location> locations) {
        Location destination;
        do {
            int num = (int) (Math.random() * 18);
            destination = locations.get(num);
        }
        while (destination.isFull() && destination.equals(origin)); // Ensures that the destination does not already have a passenger at it. Also ensures the destination is not the same as the origin.
        return destination;
    }

    /**
     * Calculates the distance between the passenger's location and their destination. This is also used to calculate the passenger's fare.
     *
     * @param origin      The passenger's origin.
     * @param destination The passenger's destination.
     * @return The distance between the passenger's origin and destination.
     */
    private double getTravelDistance(Location origin, Location destination) {
        float xDist = Math.abs(origin.getX() - destination.getX());
        float yDist = Math.abs(origin.getY() - destination.getY());
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    private int setFare(int modifier){
        int tip = MathUtils.random(0, 30);
        return (int)(getTravelDistance(origin, destination) / modifier) + tip;
    }

    public int getFare() {
        return fare;
    }

    public Location getOrigin() {
        return origin;
    }

}
