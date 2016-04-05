/*Credits: Tiledmap implementation instructions and parts of code from
 http://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx
 on 2/27/2016

AUDIO FILE FOR AMBIENT NOISE
Title: City Traffic And Construction
About: City traffic and construction sounds from busy intersection in kill devil hills north carolina
Uploaded: 06.03.09 | License: Attribution 3.0 | Recorded by Mike Koenig | File Size: 4.66 MB | Downloads: 42600

*/


package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.awt.*;
import java.util.HashMap;


public class UberGame extends ApplicationAdapter {


    //public float width = (float)screenSize.getWidth();
    //public float height =(float)screenSize.getHeight();
    public float width = 1000;
    public float height = 1000;
    SpriteBatch batch;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    float[] decelleration = new float[2];
    boolean gameStarted = false;
    private Texture taxiImg;
    private Texture person;
    private OrthographicCamera camera;
    private Car taxi;
    private Passenger passenger1;
    private Passenger passenger2;
    private HashMap<Integer, Location> locations;
    private final int NUM_LOCATIONS = 18;

    @Override
    public void create() {

        setUpScreen();
        createCar();
        createLocations();
        createPassengers();
    }

    @Override
    public void render() {
        drawGameObjects();
        play();
    }

    public void setUpScreen() {
        Gdx.graphics.setWindowedMode((int) width, (int) height);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        tiledMap = new TmxMapLoader().load("map@9March.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera.update();
    }

    public void createCar() {
        taxiImg = new Texture("tiny_car_square.png");
        taxi = new Car(taxiImg, this.camera);
        taxi.getSprite().setPosition((int) (width / 2), (int) (height / 2));
        taxi.X_pos = (int) (width / 2);
        taxi.Y_pos = (int) (height / 2);
        taxi.getSprite().setSize((int) (width / 25), (int) (width / 25));
        taxi.setOrientation(0, 1);
    }

    public void createLocations(){
        locations = new HashMap<Integer, Location>();
        locations.put(0, new Location(70, 130));
        locations.put(1, new Location(230, 10));
        locations.put(2, new Location(510, 130));
        locations.put(3, new Location(910, 10));
        locations.put(4, new Location(160, 330));
        locations.put(5, new Location(470, 210));
        locations.put(6, new Location(710, 330));
        locations.put(7, new Location(110, 410));
        locations.put(8, new Location(270, 530));
        locations.put(9, new Location(710, 410));
        locations.put(10, new Location(910, 530));
        locations.put(11, new Location(110, 930));
        locations.put(12, new Location(370, 730));
        locations.put(13, new Location(310, 930));
        locations.put(14, new Location(510, 810));
        locations.put(15, new Location(630, 610));
        locations.put(16, new Location(910, 730));
        locations.put(17, new Location(630, 930));
    }


    public void createPassengers(){
        person = new Texture("stick_figure.png");
        passenger1 = new Passenger(person, locations);
        passenger2 = new Passenger(person, locations);
    }


    public void drawGameObjects() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.begin();
        taxi.getSprite().draw(batch);
        passenger1.getSprite().draw(batch);
        passenger2.getSprite().draw(batch);
        batch.end();
    }

    public void applyFriction(float[] decelleration) {
        taxi.velociy[0] -= taxi.velociy[0] * 0.05;//decelleration[0];
        taxi.velociy[1] -= taxi.velociy[1] * 0.05;//decelleration[1];
       // taxi.driveForward(tiledMap);
    }

    public void play() {

        if (!gameStarted) {

            Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("City_Traffic.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume((float)0.1);
            backgroundMusic.play();
            gameStarted = true;
        }

        if (!(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))) {
            move(0);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!(taxi.orientation[0] == -1 && taxi.orientation[1] == 0)) {
                taxi.turnLeft(tiledMap);
                taxi.playTiresNoise();
            }
            move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (!(taxi.orientation[0] == 1 && taxi.orientation[1] == 0)) {
                taxi.turnRight(tiledMap);
                taxi.playTiresNoise();

            }
            move(25);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (!(taxi.orientation[0] == 0 && taxi.orientation[1] == 1)) {
                taxi.turnUp(tiledMap);
                taxi.playTiresNoise();
            }
            move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (!(taxi.orientation[0] == 0 && taxi.orientation[1] == -1)) {
                taxi.turnDown(tiledMap);
                taxi.playTiresNoise();
            }
            move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            taxi.restart(tiledMap);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(-1);
        }
    }


    public void move(int accelleration) {
        taxi.accellerate(tiledMap, accelleration);
        decelleration[0] = (float) (taxi.velociy[0] * 0.15);
        decelleration[1] = (float) (taxi.velociy[1] * 0.15);
        applyFriction(decelleration);
        taxi.driveForward(tiledMap);
    }
}




