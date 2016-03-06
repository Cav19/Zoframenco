package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;

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


    float shift;


    private void stay_InMap(TiledMap tiledMap){
        MapProperties mapProperties= tiledMap.getProperties();
        int mapWidth = mapProperties.get("width", Integer.class);
        int mapHeight = mapProperties.get("height", Integer.class);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        System.out.println("Xpos is: "+ X_pos + "mapwdidh is "+width);
        if (X_pos+this.getSprite().getHeight()/2 > width) {
            this.sprite.setPosition(width-this.getSprite().getHeight(), Y_pos);
            X_pos = width- this.sprite.getHeight();
        }
    }

    public void driveForward(TiledMap tiledMap, float acceleration){
        shift = acceleration * Gdx.graphics.getDeltaTime();
        if(orientation == 0){ sprite.setY(Y_pos + shift);
            Y_pos+=shift;
            stay_InMap(tiledMap);
        }
        else if(orientation == 1){
            sprite.setX(sprite.getX() + shift);
            X_pos+=shift;
            stay_InMap(tiledMap);
        }
        else if(orientation == 2){
            sprite.setY(sprite.getY() - shift);
            Y_pos-=shift;
            stay_InMap(tiledMap);
        }
        else{
            sprite.setX(sprite.getX() - shift);
            X_pos-=shift;
            stay_InMap(tiledMap);
        }
    }





    public void driveBackward(TiledMap tiledMap, float acceleration){
        shift = acceleration * Gdx.graphics.getDeltaTime();
        System.out.println("Deltatime is: " + Gdx.graphics.getDeltaTime()+ "shift is: " + shift + " sprite.x is: "+ sprite.getX());

        if(orientation == 0){
            sprite.setY(sprite.getY() - shift);
            Y_pos=Y_pos-shift;


        }
        else if(orientation == 1){
            sprite.setX(sprite.getX() - shift);
            X_pos=X_pos-shift;
        }
        else if(orientation == 2){
            sprite.setY(sprite.getY() + shift);
            Y_pos=Y_pos+shift;
        }
        else{
            sprite.setX(sprite.getX() + shift);
            X_pos=X_pos+shift;
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
