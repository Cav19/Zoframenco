package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by zoray on 3/10/16.
 */

public class PlayScreen implements Screen {

    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 1150;

    private MyGdxGame game;
    private Viewport gamePort;
    private Hud hud;
    public static TiledMap tiledMap;
    private static SpriteBatch batch;
    private Music backgroundMusic;
    public static OrthographicCamera camera = new OrthographicCamera();
    private Car taxi = new Car();
    private Passenger passenger;
    private boolean passengersWaiting = false;
    private Music moneySound = Gdx.audio.newMusic(Gdx.files.absolute("moneySound.mp3"));




    public PlayScreen(MyGdxGame game){
        this.game = game;
        batch = new SpriteBatch();
        Gdx.graphics.setWindowedMode(V_WIDTH, V_HEIGHT);
        hud = new Hud(game, batch, camera);
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        gamePort = new FitViewport(V_WIDTH, V_HEIGHT, camera);
        tiledMap = new TmxMapLoader().load("map@17April.tmx");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("City_Traffic.mp3"));
    }

    @Override
    public void show() {
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume((float)0.1);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta) {
        setUpScreen();
        drawGameObjects();
        drawHud();
        play();
        if (passengersWaiting){
            drawPassenger(passenger);
        }
    }

    private void drawHud() {
        if (passenger != null && taxi.isFull()) {
            hud.updateMessage("Drop me at " + passenger.getDestination().toString());
        }
        hud.updateTime(Gdx.graphics.getDeltaTime());
        hud.stage.draw();
    }

    private void setUpScreen(){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        camera.update();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
    }

    private void drawGameObjects() {
        drawMap();
        drawCar(taxi);
    }

    public void drawPassenger(Passenger passenger){
        batch.begin();
        passenger.getSprite().draw(batch);
        batch.end();
    }

    private void drawMap() {
        TiledMapRenderer tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public void drawCar(Car taxi){
        batch.begin();
        taxi.getSprite().draw(batch);
        batch.end();
    }

    public void highlightDestination(Passenger passenger){
        Rectangle box = passenger.getDestination().getRectangle();
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(box.getX(), box.getY(), box.getWidth(), box.getHeight(), Color.RED, Color.RED, Color.RED, Color.RED);
        renderer.end();
    }

    public void play() {

        listenToInput();

        if (!passengersWaiting){
            passenger= new Passenger(MyGdxGame.locations);
            passengersWaiting=true;
        }

        else if (passengersWaiting) {
            if (taxi.hasArrived(passenger.getOrigin())) {
                passenger.enterTaxi();
                taxi.addPassenger();
                passenger.getOrigin().removePassenger();
            }

        }

        if (taxi.isFull()){
            highlightDestination(passenger);
            Rectangle destinationRectangle= Rectangle.tmp2.setPosition(passenger.getDestination().getX(), passenger.getDestination().getY());
            destinationRectangle.setSize(25,25);
            if (taxi.hasArrived(passenger.getDestination())) {
                if (Math.abs(taxi.getVelocity()[0]) >1.5 | Math.abs(taxi.getVelocity()[1]) >1.5 ) {
                    moneySound.play();
                    Hud.addScore(passenger.getFare());
                    passenger.exitTaxi();
                    taxi.empty();
                    passenger = null;
                    passengersWaiting = false;
                }
            }
        }

    }

    private void listenToInput() {

        if (!(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))) {
            taxi.move(0, tiledMap);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!(taxi.getOrientation()[0] == -1 && taxi.getOrientation()[1] == 0)) {
                taxi.turnLeft();
                taxi.playTiresNoise();
            }
            taxi.move(25, tiledMap);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (!(taxi.getOrientation()[0] == 1 && taxi.getOrientation()[1] == 0)) {
                taxi.turnRight();
                taxi.playTiresNoise();

            }
            taxi.move(25, tiledMap);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (!(taxi.getOrientation()[0] == 0 && taxi.getOrientation()[1] == 1)) {
                taxi.turnUp();
                taxi.playTiresNoise();
            }
            taxi.move(25,tiledMap);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (!(taxi.getOrientation()[0] == 0 && taxi.getOrientation()[1] == -1)) {
                taxi.turnDown();
                taxi.playTiresNoise();
            }
            taxi.move(25, tiledMap);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            restart();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(-1);
        }

    }

    public void restart() {
        taxi.empty();
        passenger.getOrigin().removePassenger();
        passenger = null;
        passenger = new Passenger(MyGdxGame.locations);
        passengersWaiting=true;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
