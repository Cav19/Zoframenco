package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

/**
 * Created by Mengdie on 2016/4/26.
 */
public class Timer {
    private Sprite sprite;
    private float width ;
    private float height;
    private float X_pos = HomeScreen.V_WIDTH / 4.5f;
    private float Y_pos = HomeScreen.V_HEIGHT / 3.8f;
    private static Random generator = new Random();
    private boolean visible = true;

    public Timer(){
        sprite = new Sprite(new Texture("timer.png"));
        sprite.setSize(50,50);
        width = sprite.getWidth();
        height = sprite.getHeight();
        sprite.setPosition(X_pos, Y_pos);
       }

    public float getX(){
        return this.X_pos;
    }

    public float getY(){
        return this.Y_pos;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void removeTimer(){
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void randomlyPlaceTimer() {
        visible = true;
        do {
            X_pos =  generator.nextInt(HomeScreen.V_WIDTH) + 1;
            Y_pos =  generator.nextInt(HomeScreen.V_HEIGHT) + 1;
        } while(!PlayScreen.isTileType(X_pos, Y_pos+ height/2,"timer")
                ||!PlayScreen.isTileType(X_pos + width, Y_pos+ height/2,"timer")
                ||!PlayScreen.isTileType(X_pos + width/2, Y_pos,"timer")
                ||!PlayScreen.isTileType(X_pos + width/2,Y_pos+ height,"timer")
                );
        sprite.setPosition(X_pos, Y_pos);
    }

}
