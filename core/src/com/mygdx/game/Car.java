package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.awt.*;

/**
 * Created by Valenti on 3/2/2016.
 */
public class Car {
    private Sprite sprite;
    private int orientation;
    private Texture texture;
    private float X_pos=0;
    private float Y_pos=0;
    private float[] speed={0,0};


    public Car(Texture texture){
        this.texture = texture;
        this.orientation = 0;
        this.sprite = new Sprite(texture);
    }

    public float getX_pos(){
        return this.X_pos;
    }
    public float getY_pos(){
        return this.Y_pos;
    }

    public void setX_pos(float X_pos){
        this.X_pos=X_pos;
    }
    public void setY_pos(float Y_pos){
        this.Y_pos=Y_pos;
    }

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public float width = (float)screenSize.getWidth();
    public float height =(float)screenSize.getHeight();


    float shift;


    private void stay_InMap(){

        if (X_pos+this.getSprite().getHeight() > width) {
            this.sprite.setPosition(width-this.getSprite().getHeight(), Y_pos);
            X_pos = width- this.sprite.getHeight();
        }

        if (X_pos-this.getSprite().getHeight()/3 < 0) {
            this.sprite.setPosition(this.getSprite().getHeight()/3, Y_pos);
            X_pos = this.sprite.getHeight()/3;
        }

        if (Y_pos+this.getSprite().getHeight() > height) {
            this.sprite.setPosition(X_pos, height-this.getSprite().getHeight());
            Y_pos = height- this.sprite.getHeight();
        }

        if (Y_pos - this.getSprite().getHeight()/3 < 0) {
            this.sprite.setPosition(X_pos, this.getSprite().getHeight()/3);
            Y_pos = this.sprite.getHeight()/3;
        }







    }

    public void driveForward(TiledMap tiledMap, float acceleration){
        shift = acceleration * Gdx.graphics.getDeltaTime();
        if(orientation == 0){ sprite.setY(Y_pos + shift);
            Y_pos+=shift;
            stay_InMap();
        }
        else if(orientation == 1){
            sprite.setX(sprite.getX() + shift);
            X_pos+=shift;
            stay_InMap();
        }
        else if(orientation == 2){
            sprite.setY(sprite.getY() - shift);
            Y_pos-=shift;
            stay_InMap();
        }
        else{
            sprite.setX(sprite.getX() - shift);
            X_pos-=shift;
            stay_InMap();
        }
    }





    public void driveBackward(TiledMap tiledMap, float acceleration){
        shift = acceleration * Gdx.graphics.getDeltaTime();
        System.out.println("Deltatime is: " + Gdx.graphics.getDeltaTime()+ "shift is: " + shift + " sprite.x is: "+ sprite.getX());

        if(orientation == 0){
            sprite.setY(sprite.getY() - shift);
            Y_pos=Y_pos-shift;
            stay_InMap();


        }
        else if(orientation == 1){
            sprite.setX(sprite.getX() - shift);
            X_pos=X_pos-shift;
            stay_InMap();

        }
        else if(orientation == 2){
            sprite.setY(sprite.getY() + shift);
            Y_pos=Y_pos+shift;
            stay_InMap();

        }
        else{
            sprite.setX(sprite.getX() + shift);
            X_pos=X_pos+shift;
            stay_InMap();

        }
    }

    public void turnLeft(TiledMap tiledMap){
       // driveForward(tiledMap,40);
        sprite.rotate90(false);
        if(orientation == 0){
            setOrientation(3);
        }
        else{
            setOrientation(orientation - 1);
        }
    }

    public void turnRight(TiledMap tiledMap){

       // driveForward(tiledMap,40);
        sprite.rotate90(true);
        if(orientation == 3){
            setOrientation(0);
        }
        else{
            setOrientation(orientation + 1);
        }
    }

    public Sprite getSprite(){
        return sprite;
    }

    public int getOrientation(){
        return orientation;
    }

    public Texture getTexture(){
        return texture;
    }

    public void setOrientation(int num){
        this.orientation = num;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }
}
