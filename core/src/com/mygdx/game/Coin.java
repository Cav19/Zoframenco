package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

/**
 * Created by Mengdie on 2016/5/4.
 */
public class Coin {
    private Sprite sprite;
    private Texture texture = new Texture("coin.png");
    private float width ;
    private float height;
    private float X_pos = PlayScreen.V_WIDTH / 2;
    private float Y_pos = PlayScreen.V_HEIGHT / 2;;
    private static Random generator = new Random();
    private boolean visible = true;

    public Coin(){
        sprite = new Sprite(texture);
        sprite.setSize(50,50);
        width = sprite.getWidth();
        height = sprite.getHeight();
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

    public void removeCoin(){
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void randomlyPlaceCoin() {
        visible = true;
        do {
            X_pos =  generator.nextInt(PlayScreen.V_WIDTH) + 1;
            Y_pos =  generator.nextInt(PlayScreen.V_HEIGHT) + 1;
        } while(!PlayScreen.isTileType(X_pos, Y_pos+ height/2,"timer")
                ||!PlayScreen.isTileType(X_pos + width, Y_pos+ height/2,"timer")
                ||!PlayScreen.isTileType(X_pos + width/2, Y_pos,"timer")
                ||!PlayScreen.isTileType(X_pos + width/2,Y_pos+ height,"timer")
                );
        sprite.setPosition(X_pos, Y_pos);
    }

}
