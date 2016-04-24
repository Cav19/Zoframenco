package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;


public class MyGdxGame extends Game {

    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 1150;
    public static Car taxi;
    public static OrthographicCamera camera;
    boolean passengersWaiting=false;
    public Passenger passenger;
    private HashMap<Integer, Location> locations;
    //private final int NUM_LOCATIONS = 18;
    Sprite initialPosition = new Sprite();


    @Override
    public void create () {
        camera=new OrthographicCamera();
        createCar();
        createLocations();
        setScreen(new HomeScreen(this));
    }

    @Override
    public void render () {
        super.render();
        if (passengersWaiting){
            PlayScreen.drawPassenger(passenger);
        }

    }


    public void play() {

        listenToInput();

        if (!passengersWaiting){
            passenger= new Passenger(locations);
            passengersWaiting=true;
            passenger.game= this;
            camera.update();
        }

        else if (passengersWaiting) {
            if (taxiHasArrived()) {
                passenger.enterTaxi();
                taxi.isFull();
                passenger.getOrigin().removePassenger();
            }

        }

        if (taxi.isFull()){
            //addDebugDot(passenger.getDestination().getX(),passenger.getDestination().getY() );
            highlightDestination(passenger);
            Rectangle destinationRectangle= Rectangle.tmp2.setPosition(passenger.getDestination().getX(), passenger.getDestination().getY());
            destinationRectangle.setSize(25,25);
            if (taxi.hasReachedDestination(destinationRectangle)) {
                passenger.exitTaxi();
                taxi.moneySound.play();
                Hud.addScore(passenger.getFare());
                if (Math.abs(taxi.getVelocity()[0]) >1.5 | Math.abs(taxi.getVelocity()[1]) >1.5 ) {
                    taxi.empty();
                    passenger = null;
                    passengersWaiting = false;
                }
            }
        }

    }

    private boolean taxiHasArrived(){
        if(((int)taxi.getVelocity()[0]==0) && ((int)taxi.getVelocity()[1]==0) && (taxi.getSprite().getX() >= passenger.getSprite().getX() - 20 && taxi.getSprite().getX() <= passenger.getSprite().getX() + 20) && (taxi.getSprite().getY() >= passenger.getSprite().getY() - 20 && taxi.getSprite().getY() <= passenger.getSprite().getY() + 20)){
            return true;
        }
        else return false;
    }

    private void listenToInput() {

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
                taxi.turnDown();
                taxi.playTiresNoise();
            }
            taxi.move(25);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            restart();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(-1);
        }

    }

    public void createCar() {
        taxi = new Car();
    }

    public void createLocations(){
        locations = new HashMap<Integer, Location>();
        locations.put(0, new Location(60, 940));
        locations.put(1, new Location(200, 1040));
        locations.put(2, new Location(500, 940));
        locations.put(3, new Location(900, 1040));
        locations.put(4, new Location(150, 740));
        locations.put(5, new Location(450, 870));
        locations.put(6, new Location(700, 740));
        locations.put(7, new Location(95, 670));
        locations.put(8, new Location(260, 540));
        locations.put(9, new Location(700, 670));
        locations.put(10, new Location(900, 540));
        locations.put(11, new Location(350, 340));
        locations.put(12, new Location(610, 470));
        locations.put(13, new Location(900, 340));
        locations.put(14, new Location(95, 140));
        locations.put(15, new Location(300, 140));
        locations.put(16, new Location(500, 270));
        locations.put(17, new Location(610, 140));
    }



    public static void addDebugDot(float x, float y){
        //graphic debug code from http://gamedev.stackexchange.com/questions/72449/how-to-draw-a-rectangle-or-curve-between-two-co-ordinates-in-libgdx

        Texture txt = new Texture ("collision_Tile.jpg");
        Sprite debugSprite = new Sprite(txt);// our "rectangular"
        debugSprite.setPosition(x,y);
        debugSprite.setOrigin(x,y);
        debugSprite.setSize(25,25);
        debugSprite.setColor(Color.RED);
        SpriteBatch debugBatch=new SpriteBatch();
        debugBatch.begin();
        debugSprite.draw(debugBatch);
        debugBatch.end();
        MyGdxGame.camera.update();
    }

    public void highlightDestination(Passenger passenger){
        Rectangle box = passenger.getDestination().getRectangle();
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(box.getX(), box.getY(), box.getWidth(), box.getHeight(), Color.RED, Color.RED, Color.RED, Color.RED);
        renderer.end();
        MyGdxGame.camera.update();
    }

    public void restart() {
        taxi.empty();
        passenger.getOrigin().removePassenger();
        passenger = null;
        passenger = new Passenger(locations);
        passengersWaiting=true;
        passenger.game= this;
        camera.update();
    }

}
