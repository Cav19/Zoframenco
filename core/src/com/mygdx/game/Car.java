/*
TIRES NOISE CREDITS
Title: Tires Squealing
About: Sound of a truck or large cars tires squealing loud and clear. sound recorded in stereo. great city, car, or similar sound effect.
Uploaded: 11.14.09 | License: Attribution 3.0 | Recorded by Mike Koenig | File Size: 163 KB

CRASH SOUND:

Title: Strong Punch
About: Nice strong punch or punching sound effect. nice for kung fu boxing or other fight scene or game.
Uploaded: 03.04.11 | License: Attribution 3.0 | Recorded by Mike Koenig | File Size: 192 K

 */


package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;


public class Car {
    private MyGdxGame game;
    private Sprite sprite;
    private Texture texture;
    public boolean full;
    public float X_pos = 0;
    public float Y_pos = 0;
    public Camera camera;
    public float[] velocity = new float[2];
    public int[] orientation= new int[2];


    public Car(Texture texture, Camera camera) {
        this.texture = texture;
        this.orientation = orientation;
        this.sprite = new Sprite(texture);
        this.camera = camera;
        this.full = false;
        this.game=game;

    }

    public Car(MyGdxGame game){
        this.camera = game.camera;
        this.game=game;
    }

    public void setSprite(Texture texture) {
        this.sprite=new Sprite(texture);
    }

    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //public float width = (float)screenSize.getWidth();
    //public float height =(float)screenSize.getHeight();
    public float width = 1000;
    public float height = 1000;
    public float time_sinceLastNoise=Gdx.app.getGraphics().getDeltaTime();

    private float shift;

    public static boolean isCellProperty(float x, float y, TiledMap tiledMap, String property) {


        MapLayers allLayers = tiledMap.getLayers();
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) allLayers.get(0);
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return (cell != null) &&  (cell.getTile() != null)  &&  ((cell.getTile().getProperties().containsKey(property)));

    }

    //www.norakomi.com/tutorial_mambow2_collision.php

    public boolean blocked(float x, float y, TiledMap tiledMap) {
        boolean collisionWithMap = false;
        collisionWithMap = !isCellProperty(x, y, tiledMap, "road");   // isCellBLocked(x+Car.this.width, y, tiledMap) || isCellBLocked(x-Car.this.width, y, tiledMap) ||  isCellBLocked(x, (int)(y+Car.this.height), tiledMap);
        return collisionWithMap;
    }


    private boolean checkCollisions(float[] velociy, TiledMap tiledMap) {
        boolean collision = false;
        X_pos+=velociy[0];
        Y_pos+=velociy[1];

        if (X_pos + (int) this.getSprite().getWidth() >= width) {
            collision = true;
            playCollisionNoise();
            velociy[0]=0;
            velociy[1]=0;
        }
        if (X_pos <= 0) {
            collision = true;
            playCollisionNoise();

            velociy[0]=0;
            velociy[1]=0;

        }
        if (Y_pos + (int) this.getSprite().getWidth() >= height) {

            collision = true;
            playCollisionNoise();

            velociy[0]=0;
            velociy[1]=0;
        }
        if (Y_pos <= 0) {

            collision = true;
            playCollisionNoise();

            velociy[0]=0;
            velociy[1]=0;
        }

        if (blocked(X_pos, Y_pos, tiledMap) || blocked(X_pos + this.getSprite().getWidth()/2, Y_pos+this.getSprite().getHeight() , tiledMap) || blocked(X_pos, Y_pos+this.getSprite().getHeight(), tiledMap)) {
            collision = true;
            playCollisionNoise();

            velociy[0]=0;
            velociy[1]=0;
        }

        return collision;

    }

    private boolean check_BackwardCollisions(float[] velociy, TiledMap tiledMap) {
        return checkCollisions(velociy, tiledMap);
    }

    public void accellerate(TiledMap tiledMap, float accelleration){
        if (this.velocity[0]==0){ this.velocity[0]+=orientation[0]*0.3;}
        if (this.velocity[1]==0){ this.velocity[1]+=orientation[1]*0.3;}
        if ((velocity[0]>-3) && (velocity[0]<3) && (velocity[1]>-3) && (velocity[1]<3)) {
            this.velocity[0] += (float) (0.15 * Gdx.graphics.getDeltaTime() * accelleration) * orientation[0];
            this.velocity[1] += (float) (0.15 * Gdx.graphics.getDeltaTime() * accelleration) * orientation[1];
        }
        driveForward(tiledMap);
    }

    public void turnUp() {
        velocity[0]=(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);
        velocity[1]= velocity[0]*orientation[0]+ velocity[1]*orientation[1];

        if (!(orientation[0] == 0 && orientation[1] == 1)) {
            turnLeft();
            sprite.rotate90(true);
            setOrientation(0,1);
            velocity[0]= velocity[0]*orientation[0];
            velocity[1]= velocity[1]*orientation[1];
        }
    }

    public void playTiresNoise() {
        if (time_sinceLastNoise == 30) {
            time_sinceLastNoise = 1;
            Sound tiresNoise = Gdx.audio.newSound(Gdx.files.internal("tiresNoise.mp3"));


            tiresNoise.play((float)0.3);
        }
        else time_sinceLastNoise++;
    }


    public void playCollisionNoise() {
        Music collisionNoise = Gdx.audio.newMusic(Gdx.files.internal("crash.mp3"));
        collisionNoise.setPosition((float) 50);
        collisionNoise.setVolume(75);
        if (velocity[0]*orientation[0] + velocity[1]*orientation[1] !=0) {
            collisionNoise.play();
        }

        else collisionNoise.stop();

    }

    public void turnDown(TiledMap tiledMap){
        velocity[0]=(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);
        velocity[1]= velocity[0]*orientation[0]+ velocity[1]*orientation[1];

        if (!(orientation[0] == 0 && orientation[1] == -1)) {

            turnLeft();
            sprite.rotate90(false);
            setOrientation(0,-1);
            velocity[0]= velocity[0]*orientation[0];
            velocity[1]= velocity[1]*orientation[1];
        }

    }

    public void driveForward( TiledMap tiledMap) {
        float old_X = X_pos;
        float old_Y = Y_pos;
        if (!checkCollisions(velocity, tiledMap)) {
            sprite.setPosition(X_pos, Y_pos);
        } else {
            X_pos = old_X;
            Y_pos = old_Y;
        }
    }

    public void turnLeft() {
        velocity[0]=(velocity[1]*orientation[1] + velocity[0]*orientation[0]);
        velocity[1]= velocity[0]*orientation[0] + velocity[1]*orientation[1];


        if (!((orientation[0] == -1) && (orientation[1] == 0))) {

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

        velocity[0]= velocity[0]*orientation[0];
        velocity[1]= velocity[1]*orientation[1];

    }

    public void turnRight() {

        if (!((orientation[0] == 1) && (orientation[1] == 0))) {
            turnUp();
            velocity[0]=(velocity[0]*orientation[0]+ velocity[1]*orientation[1]);
            velocity[1]= velocity[0]*orientation[0]+ velocity[1]*orientation[1];
            sprite.rotate90(true);
            setOrientation(1, 0);
            velocity[0]= velocity[0]*orientation[0];
            velocity[1]= velocity[1]*orientation[1];
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
        this.orientation[0]=x;
        this.orientation[1]=y;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }


    public void move(int accelleration) {
        if (game.tiledMap==null) {
            System.out.println("Tiledmap always null");
        }
        game.taxi.accellerate(game.tiledMap, accelleration);

        game.decelleration[0] = (float) (this.velocity[0] * 0.15);
        game.decelleration[1] = (float) (this.velocity[1] * 0.15);
        game.applyFriction(game.decelleration);
        this.driveForward(game.tiledMap);
        game.camera.update();

    }

    public boolean hasReachedDestination( Rectangle destinationRectangle){
        return this.getSprite().getBoundingRectangle().overlaps(destinationRectangle);
    }

}
