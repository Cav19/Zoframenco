/*
TIRES NOISE CREDITS
Title: Tires Squealing
About: Sound of a truck or large cars tires squealing loud and clear. sound recorded in stereo. great city, car, or similar sound effect.
Uploaded: 11.14.09 | License: Attribution 3.0 | Recorded by Mike Koenig | File Size: 163 KB

CRASH SOUND:

Title: Strong Punch
About: Nice strong punch or punching sound effect. nice for kung fu boxing or other fight scene or game.
Uploaded: 03.04.11 | License: Attribution 3.0 | Recorded by Mike Koenig | File Size: 192 K

 */

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Car {
    private Sprite sprite;
    private boolean full;
    private float X_pos;
    private float Y_pos;
    private float[] velocity = new float[2];
    private Direction currentDirection;
    private int[] orientation = new int[2];
    private Passenger passenger;
    public static Direction UP= new Direction(1, "UP");
    public static Direction RIGHT=  new Direction(2, "RIGHT");
    public static Direction DOWN=  new Direction(3, "DOWN");
    public static Direction LEFT=  new Direction(4, "LEFT");


    public Car(){
        sprite = new Sprite(new Texture("images/tiny_car_square.png"));
        sprite.setSize(PlayScreen.V_WIDTH / 20, PlayScreen.V_WIDTH / 20);
        sprite.setPosition(X_pos, Y_pos);
        X_pos = PlayScreen.V_WIDTH / 2;
        Y_pos = (float)(PlayScreen.V_HEIGHT / 2.25);
        setOrientation(0, 1);
        currentDirection=UP;

    }


    private void accelerate(float acceleration){
        if (this.velocity[0]==0){ this.velocity[0]= (float) (orientation[0]*0.3);}
        if (this.velocity[1]==0){ this.velocity[1]=(float) (orientation[1]*0.3);}



        if ((velocity[0]>-5) && ((velocity[0]< 5) && (velocity[1] < 5) && (velocity[1]> - 5))) {
            this.velocity[0] += (float) (0.4 * Gdx.graphics.getDeltaTime() * acceleration) * orientation[0];
            this.velocity[1] += (float) (0.4 * Gdx.graphics.getDeltaTime() * acceleration) * orientation[1];
        }
        driveForward();
    }



    public void driveForward() {
        float old_X = X_pos;
        float old_Y = Y_pos;
        if (!PlayScreen.checkCollisions(velocity)) {
            sprite.setPosition(X_pos, Y_pos);
        } else {
            X_pos = old_X;
            Y_pos = old_Y;
        }
    }



    public void turn(String direction) {  //change to enumerator
        Direction newDirection= getDirection(direction);
        if (currentDirection.id != newDirection.id) {
              velocity[0]= (float)0.01*(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);
              velocity[1]=(float)0.01*(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);


            int rotations = 360 - (newDirection.id - currentDirection.id) * 90;


            if (newDirection == LEFT) {
                this.setOrientation(-1, 0);
                currentDirection= LEFT;
            } else if (newDirection == RIGHT) {
                this.setOrientation(1, 0);
                currentDirection= RIGHT;

            } else if (newDirection == UP) {
                this.setOrientation(0, 1);
                currentDirection= UP;

            } else if (newDirection == DOWN) {
                this.setOrientation(0, -1);
                currentDirection= DOWN;

            }


            // int r;
            sprite.rotate(rotations);
            velocity[0] = velocity[0] * orientation[0];
            velocity[1] = velocity[1] * orientation[1];

        }
    }

    private Direction getDirection(String direction) {
        if (direction=="UP"){
            return UP;
        }
        else if (direction=="DOWN"){
            return  DOWN;
        }
        else if (direction=="LEFT"){
            return LEFT;
        }
        else return  RIGHT;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int [] getOrientation() {
        return orientation;
    }

    public void setOrientation(int x, int y) {
        this.orientation[0]=x;
        this.orientation[1]=y;
    }

    public void move(float acceleration) {
        accelerate(acceleration);
        applyFriction();
    }

    private void applyFriction() {
        if (velocity[0] > -10 && velocity[1] > -10) {
            velocity[0] -= velocity[0] * 0.05;
            velocity[1] -= velocity[1] * 0.05;
        }
    }

    public boolean hasArrived(Location location){
        if((this.getSprite().getX() + this.getSprite().getWidth() / 2 >= location.getX() - 15 && this.getSprite().getX() + this.getSprite().getWidth() / 2 <= location.getX() + 15) && (this.getSprite().getY() + this.getSprite().getHeight() / 2 >= location.getY() - 15) && this.getSprite().getY() + this.getSprite().getHeight() / 2 <= location.getY() + 15){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isFull(){
        return full;
    }

    public void addPassenger(Passenger passenger){
        this.passenger = passenger;
        passenger.getOrigin().removePassenger();
        full = true;
    }

    public void empty(){
        passenger = null;
        full = false;
    }

    public float[] getVelocity(){
        return velocity;
    }

    public float getX(){
        return X_pos;
    }

    public float getY(){
        return Y_pos;
    }

    public void setX(float pos){
        X_pos = pos;
    }

    public void setY(float pos){
        Y_pos = pos;
    }

    public Passenger getPassenger(){
        return passenger;
    }
}
