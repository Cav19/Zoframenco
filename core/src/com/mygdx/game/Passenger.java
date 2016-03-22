package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ModelInfluencer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Valenti on 3/7/2016.
 */
public class Passenger {
    private Sprite sprite;
    private Texture texture;
    private float x;
    private float y;
    private Rectangle rectangle;

    private int fare;


    public Passenger(Texture texture, float width, float height){
        this.texture = texture;
        this.sprite = new Sprite(texture);
        //sprite.setRegion(x, y, 100, 100);
        setPosition(width, height);
    }

    private void setPosition(float width, float height){
        x = (float)Math.random() * width;
        y = (float)Math.random() * height;
        sprite.setX(x);
        sprite.setY(y);
    }

    /*private void getInCar(){

    }

    public boolean isCarNear(){
        if()
    }
    */
    public Sprite getSprite(){
        return sprite;
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }
}
