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
    private int[] orientation= new int[2];


    public Car(){
        sprite = new Sprite(new Texture("tiny_car_square.png"));
        sprite.setSize(PlayScreen.V_WIDTH / 20, PlayScreen.V_WIDTH / 20);
        sprite.setPosition(X_pos, Y_pos);
        X_pos = PlayScreen.V_WIDTH / 2;
        Y_pos = (float)(PlayScreen.V_HEIGHT / 2.25);
        setOrientation(0, 1);
    }


    //TO DO: revise this method to fix "hiccups" in car movement

    public void accelerate(float acceleration){
        if (this.velocity[0]==0){ this.velocity[0]= (float) (orientation[0]*0.3);}
        if (this.velocity[1]==0){ this.velocity[1]=(float) (orientation[1]*0.3);}



        if ((velocity[0]>-5) && ((velocity[0]< 5) && (velocity[1] < 5) && (velocity[1]> - 5))) {
            this.velocity[0] += (float) (0.4 * Gdx.graphics.getDeltaTime() * acceleration) * orientation[0];
            this.velocity[1] += (float) (0.4 * Gdx.graphics.getDeltaTime() * acceleration) * orientation[1];
        }
        driveForward();
    }

    public void turnUp() {
        velocity[0]= (float)0.01*(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);
        velocity[1]=(float)0.01*(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);

        if (!(orientation[0] == 0 && orientation[1] == 1)) {
            turnLeft();
            sprite.rotate90(true);
            setOrientation(0,1);
            velocity[0]= velocity[0]*orientation[0];
            velocity[1]= velocity[1]*orientation[1];
        }
    }


    public void turnDown(){
        velocity[0]=(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);
        velocity[1]= velocity[0]*orientation[0]+ velocity[1]*orientation[1];

        if (!(orientation[0] == 0 && orientation[1] == -1)) {

            turnLeft();
            sprite.rotate90(false);
            setOrientation(0,-1);
            velocity[0]= velocity[0]*orientation[0];
            velocity[1]= velocity[1]*orientation[1];
        }

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

    public void turnLeft() {
        velocity[0]=(velocity[1]*orientation[1] + velocity[0]*orientation[0]);
        velocity[1]= velocity[0]*orientation[0] + velocity[1]*orientation[1];


        if (!((orientation[0] == -1) && (orientation[1] == 0))) {

            if (orientation[0] == 0 && orientation[1] == 1) {
                sprite.rotate90(false);
            } else if (orientation[0] == 0 && orientation[1] == -1) {
                sprite.rotate90(true);
            } else if (orientation[0] == 1 && orientation[1] == 0) {
                sprite.rotate90(false);
                sprite.rotate90(false);
            }
        }
        setOrientation(-1, 0);

        velocity[0]= velocity[0]*orientation[0];
        velocity[1]= velocity[1]*orientation[1];

    }

    public void turnRight() {
        if (!((orientation[0] == 1) && (orientation[1] == 0))) {
            turnUp();
            velocity[0]=(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);
            velocity[1]= velocity[0]*orientation[0]+ velocity[1]*orientation[1];
            sprite.rotate90(true);
            setOrientation(1, 0);
            velocity[0]= velocity[0]*orientation[0];
            velocity[1]= velocity[1]*orientation[1];
        }
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

    public void applyFriction() {
        if (velocity[0] > -10 && velocity[1] > -10) {
            velocity[0] -= velocity[0] * 0.05;
            velocity[1] -= velocity[1] * 0.05;
        }
    }

    public boolean hasArrived(Location location){
        if((this.getSprite().getX() + this.getSprite().getWidth() / 2 >= location.getX() - 10 && this.getSprite().getX() + this.getSprite().getWidth() / 2 <= location.getX() + 10) && (this.getSprite().getY() + this.getSprite().getHeight() / 2 >= location.getY() - 10) && this.getSprite().getY() + this.getSprite().getHeight() / 2 <= location.getY() + 10){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isFull(){
        return full;
    }

    public void addPassenger(){
        full = true;
    }

    public void empty(){
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
}
