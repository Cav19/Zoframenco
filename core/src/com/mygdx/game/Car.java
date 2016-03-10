package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.awt.*;

/**
 * Created by Valenti on 3/2/2016.
 */

public class Car {
    private Sprite sprite;
    private int orientation;
    private Texture texture;
    public float X_pos = 0;
    public float Y_pos = 0;
    public Camera camera;


    public Car(Texture texture, Camera camera) {
        this.texture = texture;
        this.orientation = 0;
        this.sprite = new Sprite(texture);
        this.camera = camera;
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
        collisionWithMap = isCellBLocked(x, y, tiledMap); // isCellBLocked(x+Car.this.width, y, tiledMap) || isCellBLocked(x-Car.this.width, y, tiledMap) ||  isCellBLocked(x, (int)(y+Car.this.height), tiledMap);
        return collisionWithMap;
    }


    private boolean check_ForwardCollisions(float shift, TiledMap tiledMap) {
        boolean collision = false;

        if (orientation == 0) {
            Y_pos += shift;
        } else if (orientation == 1) {
            X_pos += shift;
        } else if (orientation == 2) {
            Y_pos -= shift;
        } else X_pos -= shift;

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

        if (blocked(X_pos, Y_pos, tiledMap) || blocked(X_pos + 20, Y_pos, tiledMap) || blocked(X_pos, Y_pos + 15, tiledMap)) {
            collision = true;
        }
        return collision;

    }

    private boolean check_BackwardCollisions(float shift, TiledMap tiledMap) {
        return check_ForwardCollisions(-shift, tiledMap);
    }


    public void driveForward(TiledMap tiledMap, float velocity) {
        shift = (int) (velocity * Gdx.graphics.getDeltaTime());
        float old_X = X_pos;
        float old_Y = Y_pos;
        if (!check_ForwardCollisions(shift, tiledMap)) {
            sprite.setPosition(X_pos, Y_pos);
        } else {
            X_pos = old_X;
            Y_pos = old_Y;
        }
    }


    public void driveBackward(TiledMap tiledMap, float velocity) { // to be fixed, it is currently same as forward
        shift = (int) (velocity * Gdx.graphics.getDeltaTime());

        float old_X = X_pos;
        float old_Y = Y_pos;
        if (!check_BackwardCollisions(shift, tiledMap)) {
            sprite.setPosition(X_pos, Y_pos);
        } else {
            X_pos = old_X;
            Y_pos = old_Y;
        }
    }


    public void turnLeft(TiledMap tiledMap) {

        driveForward(tiledMap, 100);
        sprite.rotate90(false);
        if (orientation == 0) {
            setOrientation(3);
        } else {
            setOrientation(orientation - 1);
        }
    }

    public void turnRight(TiledMap tiledMap) {

        driveForward(tiledMap, 100);
        sprite.rotate90(true);
        if (orientation == 3) {
            setOrientation(0);
        } else {
            setOrientation(orientation + 1);
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getOrientation() {
        return orientation;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setOrientation(int num) {
        this.orientation = num;
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
