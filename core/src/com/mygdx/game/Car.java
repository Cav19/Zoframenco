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
import com.badlogic.gdx.math.Rectangle;

public class Car {
    private Sprite sprite;
    private boolean full;
    private float X_pos;
    private float Y_pos;
    private float[] velocity = new float[2];
    private Direction currentDirection;
    private int[] orientation = new int[2];
    private Passenger passenger;
    public static Direction UP= new Direction(1, "UP",0,1,360);
    public static Direction RIGHT=  new Direction(2, "RIGHT",1,0,-270);
    public static Direction DOWN=  new Direction(3, "DOWN",0,-1, -180);
    public static Direction LEFT=  new Direction(4, "LEFT",-1,0, -90);
    public float currentAngle= 0;



    public Car(){
        sprite = new Sprite(new Texture("images/48car.png"));
        sprite.setSize(48, 48);
        X_pos = PlayScreen.V_WIDTH / 2;
        Y_pos = (float)(PlayScreen.V_HEIGHT / 2.3);
        sprite.setPosition(X_pos, Y_pos);
        setOrientation(0, 1);
        currentDirection=UP;

    }


    public void drawDebugRect(){
        Rectangle bounds= this.getSprite().getBoundingRectangle();

    }


    private void accelerate(float acceleration){
        if (this.velocity[0]==0){ this.velocity[0]= (float) (orientation[0]*0.3);}
        if (this.velocity[1]==0){ this.velocity[1]=(float) (orientation[1]*0.3);}



        if ((velocity[0]>-5) && ((velocity[0]< 5) && (velocity[1] < 5) && (velocity[1]> - 5))) {
            this.velocity[0] += (float)(Gdx.graphics.getDeltaTime() * acceleration) * orientation[0];
            this.velocity[1] += (float) ( Gdx.graphics.getDeltaTime() * acceleration) * orientation[1];
        }
        driveForward();
    }



    private void driveForward() {
        float oldX = X_pos;
        float oldY = Y_pos;
        if (!PlayScreen.checkCollisions()) {
            sprite.setPosition(X_pos, Y_pos);
        } else {
            X_pos = oldX;
            Y_pos = oldY;
            collide();
        }
    }

    public void collide(){
        PlayScreen.playCollisionNoise();
        velocity[0] = 0;
        velocity[1] = 0;
    }

    public void turn(String direction) {  //change to enumerator
        Direction newDirection= getDirection(direction);
        if (currentAngle != newDirection.angle) {
              //velocity[0]= (float)0.01*(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);
             // velocity[1]=(float)0.01*(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);


            int roationAngle=  (int) (newDirection.angle-currentAngle);
            sprite.rotate(-roationAngle);
            currentAngle= currentAngle+ roationAngle;

            if (Math.abs(currentAngle)>360){
                currentAngle=Math.abs(currentAngle)-360;
            }
            if (currentAngle == newDirection.angle) {
                currentDirection = newDirection;
            }
            System.out.println(currentAngle);
            sprite.setPosition(this.X_pos, this.Y_pos);

        }
    }

    public Direction getDirection(String direction) {
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
        if (velocity[0] > -10) {
            velocity[0] -= velocity[0] * 0.05;
        }
        if (velocity[1] > -10) {
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


    public void setVelocity(float x, float y){
        this.velocity[0]=x;
        this.velocity[1]=y;
    };

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
