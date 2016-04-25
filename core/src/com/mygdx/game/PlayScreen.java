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
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
    private static TiledMap tiledMap;
    private static SpriteBatch batch;
    private Music backgroundMusic;
    private static OrthographicCamera camera = new OrthographicCamera();
    private static Car taxi = new Car();
    private Passenger passenger;
    private boolean passengersWaiting = false;
    private Music moneySound = Gdx.audio.newMusic(Gdx.files.absolute("moneySound.mp3"));
    private Music tiresNoise = Gdx.audio.newMusic(Gdx.files.internal("tiresNoise.mp3"));
    private static Music collisionNoise = Gdx.audio.newMusic(Gdx.files.internal("crash.mp3"));
    private float time_sinceLastNoise=Gdx.app.getGraphics().getDeltaTime();


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
            taxi.move(0);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!(taxi.getOrientation()[0] == -1 && taxi.getOrientation()[1] == 0)) {
                taxi.turnLeft();
                playTiresNoise();
            }
            taxi.move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (!(taxi.getOrientation()[0] == 1 && taxi.getOrientation()[1] == 0)) {
                taxi.turnRight();
                playTiresNoise();

            }
            taxi.move(25);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (!(taxi.getOrientation()[0] == 0 && taxi.getOrientation()[1] == 1)) {
                taxi.turnUp();
                playTiresNoise();
            }
            taxi.move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (!(taxi.getOrientation()[0] == 0 && taxi.getOrientation()[1] == -1)) {
                taxi.turnDown();
                playTiresNoise();
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


    public static boolean checkCollisions(float[] velocity) {
        boolean collision = false;
        taxi.setX(taxi.getX() + velocity[0]);
        taxi.setY(taxi.getY() + velocity[1]);

        if (taxi.getX() + (int) taxi.getSprite().getWidth() >= PlayScreen.V_WIDTH) {
            collision = true;
            playCollisionNoise();
            velocity[0]=0;
            velocity[1]=0;
        }
        if (taxi.getX()<= 0) {
            collision = true;
            playCollisionNoise();

            velocity[0]=0;
            velocity[1]=0;

        }
        if (taxi.getY()+ (int) taxi.getSprite().getWidth() >= PlayScreen.V_HEIGHT) {

            collision = true;
            playCollisionNoise();

            velocity[0]=0;
            velocity[1]=0;
        }
        if (taxi.getY() <= 0) {

            collision = true;
            playCollisionNoise();

            velocity[0]=0;
            velocity[1]=0;
        }

        if (blocked(taxi.getX() + taxi.getSprite().getWidth()/4, taxi.getY() + taxi.getSprite().getWidth()/4, tiledMap) || blocked(taxi.getX() + taxi.getSprite().getWidth()/4, taxi.getY() + taxi.getSprite().getHeight()/(float) 1.5 , tiledMap) || blocked(taxi.getX() + taxi.getSprite().getWidth()/4, taxi.getY() + taxi.getSprite().getHeight()/ (float) 1.5, tiledMap)) {
            collision = true;
            playCollisionNoise();

            velocity[0]=0;
            velocity[1]=0;
        }

        return collision;

    }

    private static boolean blocked(float x, float y, TiledMap tiledMap) {
        return !isCellProperty(x, y, tiledMap, "road");
    }

    private static boolean isCellProperty(float x, float y, TiledMap tiledMap, String property) {
        MapLayers allLayers = tiledMap.getLayers();
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) allLayers.get(0);
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return (cell != null) &&  (cell.getTile() != null)  &&  ((cell.getTile().getProperties().containsKey(property)));
    }

    public void playTiresNoise() {
        if (time_sinceLastNoise == 30) {
            time_sinceLastNoise = 1;
            tiresNoise.play();
        }
        else time_sinceLastNoise++;
    }


    public static void playCollisionNoise() {
        collisionNoise.setPosition((float) 50);
        collisionNoise.setVolume(75);
        if (taxi.getVelocity()[0]*taxi.getOrientation()[0] + taxi.getVelocity()[1]*taxi.getOrientation()[1] !=0) {
            collisionNoise.play();
        }
        else collisionNoise.stop();
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
