package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.Random;

/**
 * Created by Mengdie on 2016/4/26.
 */
public class Timer {

    private Sprite sprite;
    private Texture texture = new Texture("timer.png");
    public float X_pos;
    public float Y_pos;
    //private static TiledMap tiledMap = new TmxMapLoader().load("map_assets/map@17April.tmx");
    public boolean poweredUp;


    public Timer(){
        sprite = new Sprite(texture);
        sprite.setSize(60,40);
        //Random generator = new Random();
        /*X_pos =  generator.nextInt(PlayScreen.V_WIDTH) + 1;
        Y_pos =  generator.nextInt(PlayScreen.V_HEIGHT) + 1;
        checkPosition(X_pos, Y_pos);
        */
        X_pos = PlayScreen.V_WIDTH / 2 + 5;
        Y_pos = (float)(PlayScreen.V_HEIGHT / 2.3);
        sprite.setPosition(X_pos, Y_pos);
        this.poweredUp = true;

       }

    public Sprite getSprite(){
        return sprite;
    }

    public void removeTimer(){
        sprite.setAlpha(0);
    }

    /*
    public void checkPosition(float X_pos, float Y_pos){
        Random generator = new Random();
        if (!PlayScreen.isTileType(X_pos,Y_pos,"road")){
            this.X_pos = X_pos;
            this.Y_pos = Y_pos;
        }
        while(PlayScreen.isTileType(X_pos,Y_pos,"road")) {
            this.X_pos =  generator.nextInt(PlayScreen.V_WIDTH) + 1;
            this.Y_pos =  generator.nextInt(PlayScreen.V_HEIGHT) + 1;
        }
    } */







}
