package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Valenti on 3/2/2016.
 */
public class Car {
    private Sprite sprite;
    private int orientation;
    private Texture texture;

    public Car(Texture texture){
        this.texture = texture;
        this.orientation = 0;
        this.sprite = new Sprite(texture);
    }

    public void driveForward(){
        if(orientation == 0){
            sprite.setY(sprite.getY() + 200 * Gdx.graphics.getDeltaTime());
        }
        else if(orientation == 1){
            sprite.setX(sprite.getX() + 200 * Gdx.graphics.getDeltaTime());
        }
        else if(orientation == 2){
            sprite.setY(sprite.getY() - 200 * Gdx.graphics.getDeltaTime());
        }
        else{
            sprite.setX(sprite.getX() - 200 * Gdx.graphics.getDeltaTime());
        }
    }

    public void driveBackward(){
        if(orientation == 0){
            sprite.setY(sprite.getY() - 200 * Gdx.graphics.getDeltaTime());
        }
        else if(orientation == 1){
            sprite.setX(sprite.getX() - 200 * Gdx.graphics.getDeltaTime());
        }
        else if(orientation == 2){
            sprite.setY(sprite.getY() + 200 * Gdx.graphics.getDeltaTime());
        }
        else{
            sprite.setX(sprite.getX() + 200 * Gdx.graphics.getDeltaTime());
        }
    }

    public void turnLeft(){
        sprite.rotate90(false);
        if(orientation == 0){
            setOrientation(3);
        }
        else{
            setOrientation(orientation - 1);
        }
    }

    public void turnRight(){
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
