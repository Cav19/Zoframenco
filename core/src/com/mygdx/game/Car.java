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

    public void driveForward(){
        shift = 50 * Gdx.graphics.getDeltaTime();
        if(orientation == 0){ sprite.setY(Y_pos + shift);
            Y_pos+=shift;

        }
        else if(orientation == 1){
            sprite.setX(sprite.getX() + shift);
            X_pos+=shift;

        }
        else if(orientation == 2){
            sprite.setY(sprite.getY() - shift);
            Y_pos-=shift;
        }
        else{
            sprite.setX(sprite.getX() - shift);
            X_pos-=shift;

        }
    }




    public void driveBackward(){
        shift = 200 * Gdx.graphics.getDeltaTime();
        if(orientation == 0){
            sprite.setY(sprite.getY() - shift);


        }
        else if(orientation == 1){
            sprite.setX(sprite.getX() - shift);
        }
        else if(orientation == 2){
            sprite.setY(sprite.getY() + shift);
        }
        else{
            sprite.setX(sprite.getX() + shift);
        }
    }

    public void turnLeft(){
        driveForward();
        driveForward();
        driveForward();
        driveForward();


        sprite.rotate90(false);
        if(orientation == 0){
            setOrientation(3);
        }
        else{
            setOrientation(orientation - 1);
        }
    }

    public void turnRight(){
        driveForward();
        driveForward();

        driveForward();
        driveForward();

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
