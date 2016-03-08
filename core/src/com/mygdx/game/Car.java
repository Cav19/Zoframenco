package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
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

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public float width = (float)screenSize.getWidth();
    public float height =(float)screenSize.getHeight();


    float shift;

    public boolean isCellBLocked(float x, float y, TiledMap tiledMap) {

        MapLayers allLayers= tiledMap.getLayers();
        TiledMapTileLayer collisionLayer= (TiledMapTileLayer) allLayers.get(1);

        TiledMapTileLayer.Cell cell = collisionLayer.getCell(
                (int) (x / collisionLayer.getTileWidth()),
                (int) (y / collisionLayer.getTileHeight()));

        return cell != null && cell.getTile() != null
                && cell.getTile().getProperties().containsKey("blocked");
    }


    public boolean blocked(float x, float y, TiledMap tiledMap) {
        boolean collisionWithMap = false; //http://www.norakomi.com/tutorial_mambow2_collision.php
        collisionWithMap = isCellBLocked(x, y, tiledMap);
        return collisionWithMap;
    }

    boolean collision=false;

    private boolean check_ForwardCollisions(float shift, TiledMap tiledMap) {
        collision = false;

        if (orientation == 0) {
            Y_pos += shift;
        } else if (orientation == 1) {
            X_pos += shift;
        } else if (orientation == 2) {
            Y_pos -= shift;
        } else X_pos -= shift;

        if (X_pos + this.getSprite().getWidth() >= width) {
            collision = true;
        }
        if (X_pos <= 0) {
            collision = true;
        }
        if (Y_pos + this.getSprite().getWidth() >= height) {
            collision = true;
        }
        if (Y_pos <= 0) {
            collision = true;
        }

        if (isCellBLocked(X_pos, Y_pos, tiledMap) ){
            collision=true;
        }
            return collision;

    }

    private boolean check_BackwardCollisions(float shift, TiledMap tiledMap){
        return check_ForwardCollisions(-shift, tiledMap);
    }



    public void driveForward(TiledMap tiledMap, float velocity){
        shift = velocity * Gdx.graphics.getDeltaTime();
        float old_X=X_pos;
        float old_Y=Y_pos;
        if (!check_ForwardCollisions(shift, tiledMap)){
            sprite.setPosition(X_pos, Y_pos);
        }
        else {
            X_pos=old_X;
            Y_pos=old_Y;
        }
        collision=false;
    }



    public void driveBackward(TiledMap tiledMap, float velocity){ // to be fixed, it is currently same as forward
        shift = velocity * Gdx.graphics.getDeltaTime();

        float old_X=X_pos;
        float old_Y=Y_pos;
        if (!check_BackwardCollisions(shift, tiledMap)){
            sprite.setPosition(X_pos, Y_pos);
        }
        else {
            X_pos=old_X;
            Y_pos=old_Y;
        }
        collision=false;
    }

    public void turnLeft(){
       // driveForward(tiledMap,40);
        sprite.rotate90(false);
        if(orientation == 0){
            setOrientation(3);
        }
        else{
            setOrientation(orientation - 1);
        }
    }

    public void turnRight(){

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


    public void restart(TiledMap tiledmap){
        collision=false;
        Y_pos=height/2;
        X_pos=width/2;
        this.setX_pos(X_pos);
        this.setY_pos(Y_pos);
        this.sprite.setPosition(X_pos,Y_pos);
    }
}
