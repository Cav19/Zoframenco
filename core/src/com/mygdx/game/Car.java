package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import java.awt.*;

/**
 * Created by Valenti on 3/2/2016.
 */

public class Car {
    private Sprite sprite;
    private Texture texture;
    public float X_pos = 0;
    public float Y_pos = 0;
    public Camera camera;
    public float[] velociy= new float[2];
    public int[] orientation= new int[2];


    public Car(Texture texture, Camera camera) {
        this.texture = texture;
        this.orientation = orientation;
        this.sprite = new Sprite(texture);
        this.camera = camera;
        this.velociy=velociy;
    }


    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //public float width = (float)screenSize.getWidth();
    //public float height =(float)screenSize.getHeight();
    public float width = 1000;
    public float height = 1000;


    private float shift;

    public boolean isCellBLocked(float x, float y, TiledMap tiledMap) {


        MapLayers allLayers = tiledMap.getLayers();
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) allLayers.get(1);
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));

        return (cell != null) &&  (cell.getTile() != null)  &&  (!(cell.getTile().getProperties().containsKey("road")));

    }

    //www.norakomi.com/tutorial_mambow2_collision.php

    public boolean blocked(float x, float y, TiledMap tiledMap) {
        boolean collisionWithMap = false;
        collisionWithMap = isCellBLocked(x, y, tiledMap);   // isCellBLocked(x+Car.this.width, y, tiledMap) || isCellBLocked(x-Car.this.width, y, tiledMap) ||  isCellBLocked(x, (int)(y+Car.this.height), tiledMap);
        return collisionWithMap;
    }


    private boolean checkCollisions(float[] velociy, TiledMap tiledMap) {
        boolean collision = false;
        X_pos+=velociy[0];
        Y_pos+=velociy[1];

        if (X_pos + (int) this.getSprite().getWidth() >= width) {
            collision = true;
        }
        if (X_pos <= 0) {
            collision = true;

        }
        if (Y_pos + (int) this.getSprite().getWidth() >= height) {

            collision = true;
        }
        if (Y_pos <= 0) {

            collision = true;
        }

        if (blocked(X_pos, Y_pos, tiledMap) || blocked(X_pos + 15, Y_pos+5, tiledMap) || blocked(X_pos, Y_pos + 25, tiledMap)) {
            collision = true;
        }

        return collision;

    }

    private boolean check_BackwardCollisions(float[] velociy, TiledMap tiledMap) {
        return checkCollisions(velociy, tiledMap);
    }

    public void accellerate(TiledMap tiledMap, float accelleration){
        if (this.velociy[0]==0){ this.velociy[0]+=orientation[0]*0.3;}
        if (this.velociy[1]==0){ this.velociy[1]+=orientation[1]*0.3;}
        if ((velociy[0]>-3) && (velociy[0]<3) && (velociy[1]>-3) && (velociy[1]<3)) {
            this.velociy[0] += (float) (0.1 * Gdx.graphics.getDeltaTime() * accelleration) * orientation[0];
            this.velociy[1] += (float) (0.1 * Gdx.graphics.getDeltaTime() * accelleration) * orientation[1];
        }
        System.out.println("Current velocity is "+this.velociy[0] + " "+ this.velociy[1]  );
        driveForward(tiledMap);

    }

    public void turnUp(TiledMap tiledMap) {
        velociy[0]=0;
        if (!(orientation[0] == 0 && orientation[1] == 1)) {
            turnLeft(tiledMap);
            sprite.rotate90(true);
            setOrientation(0,1);
        }
    }

    public void turnDown(TiledMap tiledMap){
        velociy[0]=0;
        if (!(orientation[0] == 0 && orientation[1] == -1)) {
            turnLeft(tiledMap);
            sprite.rotate90(false);
            setOrientation(0,-1);
        }

    }

    public void driveForward( TiledMap tiledMap) {
        float old_X = X_pos;
        float old_Y = Y_pos;
        if (!checkCollisions(velociy, tiledMap)) {
            sprite.setPosition(X_pos, Y_pos);
            } else {
                X_pos = old_X;
                Y_pos = old_Y;
            }
        }


/*
    public void driveBackward(TiledMap tiledMap, float DeltaTime) { // to be fixed, it is currently same as forward
        shift = (int) (velocity * Gdx.graphics.getDeltaTime());

        float old_X = X_pos;
        float old_Y = Y_pos;
        if (!check_BackwardCollisions(velocity, tiledMap)) {
            sprite.setPosition(X_pos, Y_pos);
        } else {
            X_pos = old_X;
            Y_pos = old_Y;
        }
    }

    */


    public void turnLeft(TiledMap tiledMap) {
        velociy[1]=0;
        if (this.velociy[0]>0){velociy[0]=0;}

        if (!((orientation[0] == -1) && (orientation[1] == 0))) {
            //driveForward(tiledMap, 100);
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
    }

    public void turnRight(TiledMap tiledMap) {
        velociy[1]=0;

        if (!((orientation[0] == 1) && (orientation[1] == 0))) {

            turnUp(tiledMap);
            sprite.rotate90(true);
            setOrientation(1, 0);
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int []  getOrientation() {
        return orientation;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setOrientation(int x, int y) {
        //System.out.println("Setting orientation to "+x+" and "+ y);
        this.orientation[0]=x;
        this.orientation[1]=y;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }


    public void restart(TiledMap tiledmap) {
        Y_pos = (int) (height / 2);
        X_pos = (int) (width / 2);
        this.sprite.setPosition(X_pos, Y_pos);
    }
}
