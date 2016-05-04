package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

/**
 * Created by Mengdie on 2016/4/26.
 */
public class Timer {

    private Sprite sprite;
    private Texture texture = new Texture("timer.png");
    private float X_pos = PlayScreen.V_WIDTH / 2;
    private float Y_pos = PlayScreen.V_HEIGHT / 2;;
    private static Random generator = new Random();
    private boolean visible = true;

    public Timer(){
        sprite = new Sprite(texture);
        sprite.setSize(60,40);
        //this.X_pos=x;
        //this.Y_pos=y;
       }

    public float getX(){
        return this.X_pos;
    }

    public float getY(){
        return this.Y_pos;
    }

    /*public void setX(float x_pos) {
        X_pos = x_pos;
    }

    public void setY(float y_pos) { Y_pos = y_pos; } */

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
            X_pos =  generator.nextInt(PlayScreen.V_WIDTH) + 1;
            Y_pos =  generator.nextInt(PlayScreen.V_HEIGHT) + 1;
        } while(!PlayScreen.isTileType(X_pos,Y_pos,"road")
                );
        sprite.setPosition(X_pos, Y_pos);
    }

}
