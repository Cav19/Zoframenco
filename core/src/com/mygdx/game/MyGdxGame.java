package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.HashMap;


public class MyGdxGame extends Game {

    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 1000;
    public SpriteBatch batch;
    public BitmapFont font;
    public boolean gameStarted = false;
    private Texture taxiImg;
    public static Car taxi;
    TiledMap tiledMap;
    public static OrthographicCamera camera;

    float[] decelleration = new float[2];
    private Passenger passenger1;
    private Passenger passenger2;
    private HashMap<Integer, Location> locations;
    private final int NUM_LOCATIONS = 18;
    private Texture person;
    Sprite initialPosition= new Sprite();






    @Override
    public void create () {

        this.tiledMap = new TiledMap();
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera=new OrthographicCamera();
        createCar();
        createLocations();
        createPassengers();
        setScreen(new HomeScreen(this));

    }




    public void createCar() {
        taxiImg = new Texture("tiny_car_square.png");
        taxi = new Car(this);
        taxi.setTexture(taxiImg);
        taxi.setSprite(taxiImg);
        initialPosition.setPosition( (float) V_WIDTH / 25, (float)( V_HEIGHT / 2.45));
        taxi.X_pos = initialPosition.getX();
        taxi.Y_pos = initialPosition.getY();




        taxi.getSprite().setSize((int) ( V_WIDTH / 100), (int) ( V_HEIGHT / 100));
        taxi.getSprite().setPosition(taxi.X_pos, taxi.Y_pos);
        taxi.setOrientation(0, 1);
    }


    public void createPassengers(){
        person = new Texture("stick_figure.png");
        passenger1 = new Passenger(person, locations);
        passenger2 = new Passenger(person, locations);
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




    @Override
    public void render () {
        super.render();

    }

    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }



    public void applyFriction(float[] decelleration) {
        if (taxi.velociy[0] > -10 && taxi.velociy[1] > -10) {
            taxi.velociy[0] -= taxi.velociy[0] * 0.05;//decelleration[0];
            taxi.velociy[1] -= taxi.velociy[1] * 0.05;//decelleration[1];
            // taxi.driveForward(tiledMap);
        }
    }
    public static void addDebugDot(float x, float y){
        //graphic debug code from http://gamedev.stackexchange.com/questions/72449/how-to-draw-a-rectangle-or-curve-between-two-co-ordinates-in-libgdx
        //

        Texture txt = new Texture ("collision_Tile.jpg");
        Sprite debugSprite = new Sprite(txt);// our "rectangular"
        debugSprite.setPosition(x,y);
        debugSprite.setOrigin(x,y);
        debugSprite.setSize(10,10);
        debugSprite.setColor(Color.RED);
        SpriteBatch debugBatch=new SpriteBatch();
        debugBatch.begin();
        debugSprite.draw(debugBatch);
        debugBatch.end();
        //end of debug code
        MyGdxGame.camera.update();

    }

    public void play() {

        // addDebugDot(taxi.X_pos, taxi.Y_pos);

        if (!gameStarted) {

            Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("City_Traffic.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume((float)0.1);
            backgroundMusic.play();
            gameStarted = true;
        }

        if (!(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))) {
            taxi.move(0);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!(taxi.orientation[0] == -1 && taxi.orientation[1] == 0)) {
                taxi.turnLeft();
                taxi.playTiresNoise();
            }
            taxi.move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (!(taxi.orientation[0] == 1 && taxi.orientation[1] == 0)) {
                taxi.turnRight();
                taxi.playTiresNoise();

            }
            taxi.move(25);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (!(taxi.orientation[0] == 0 && taxi.orientation[1] == 1)) {
                taxi.turnUp();
                taxi.playTiresNoise();
            }
            taxi.move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (!(taxi.orientation[0] == 0 && taxi.orientation[1] == -1)) {
                taxi.turnDown(tiledMap);
                taxi.playTiresNoise();
            }
            taxi.move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            taxi.restart(tiledMap);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(-1);
        }
    }





}
