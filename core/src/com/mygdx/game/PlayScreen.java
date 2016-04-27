package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.Array;
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
    private static OrthographicCamera camera;
    private static Car taxi = new Car();
    private Array<Passenger> allPassengers = new Array<Passenger>();
    private boolean passengersWaiting = false;
    private static soundPlayer gameSoundPlayer;


    public PlayScreen(MyGdxGame game){
        this.game = game;
        batch = new SpriteBatch();
        Gdx.graphics.setWindowedMode(V_WIDTH, V_HEIGHT);

        camera = new OrthographicCamera();
        hud = new Hud(game, batch, camera);
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);

        gamePort = new FitViewport(V_WIDTH, V_HEIGHT, camera);

        tiledMap = new TmxMapLoader().load("map@17April.tmx");
        gameSoundPlayer = new soundPlayer();
    }

    @Override
    public void show() {
       playBackGroundMusic();
    }

    /**
     * Plays background sound effect.
     */
    private void playBackGroundMusic(){
        gameSoundPlayer.playBackGroundMusic();
    }

    /**
     * Renders the game objects onto the screen.
     * @param delta
     */
    @Override
    public void render(float delta) {
        setUpScreen();
        drawMap();
        drawCar(taxi);
        drawHud();
        play();
        if (passengersWaiting){
            for(Passenger pass : allPassengers){
                drawPassenger(pass);
            }
        }

        if (Gdx.input.isTouched()) {
            //game.setScreen(new com.mygdx.game.EndScreen(game));
            dispose();
        }
    }

    /**
     * Draws the hud at the bottom of the screen.
     */
    private void drawHud() {
        if (taxi.isFull()) {
            hud.updateMessage("Drop me at " + taxi.getPassenger().getDestination().getName());
        }
        hud.updateTime(Gdx.graphics.getDeltaTime());
        hud.stage.draw();
    }

    /**
     * Sets up the main game screen and initiates the camera's view.
     */
    private void setUpScreen(){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        camera.update();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
    }

    /**
     * Draws a passenger onto the screen at their designated location.
     * @param passenger The instance of passenger to be drawn.
     */
    private void drawPassenger(Passenger passenger){
        batch.begin();
        passenger.getSprite().draw(batch);
        batch.end();
    }

    /**
     * Draws the TiledMap to the screen.
     */
    private void drawMap() {
        TiledMapRenderer tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    /**
     * Draws the user's car onto the screen.
     * @param taxi The instance of car to be drawn.
     */
    private void drawCar(Car taxi){
        batch.begin();
        taxi.getSprite().draw(batch);
        batch.end();
    }

    /**
     * Highlights the destination of the passenger currently in the user's car.
     * @param destination The destination on the map to be highlighted.
     */
    private void highlightDestination(Location destination){
        Gdx.gl20.glLineWidth(2.85f);
        Rectangle box = destination.getRectangle();
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(camera.combined);
        renderer.updateMatrices();
        renderer.setColor(Color.RED);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
        renderer.end();
    }


    /**
     * The main play function of the game which controls the game flow and individual game states.
     */
    private void play() {
        listenToInput();

        if (!passengersWaiting){
            spawnPassengers();
        }

        for(Passenger pass : allPassengers){
            if(taxi.hasArrived(pass.getOrigin())){
                taxi.addPassenger(pass);
                clearLocations();
                allPassengers.clear();
            }
        }

        if (taxi.isFull()){
            highlightDestination(taxi.getPassenger().getDestination());
            if (taxi.hasArrived(taxi.getPassenger().getDestination())) {
                gameSoundPlayer.playMoneySound();
                Hud.addScore(taxi.getPassenger().getFare());
                taxi.empty();
                passengersWaiting = false;
            }
        }

    }

    private void spawnPassengers(){
        for (int i = 0; i < 3; i++) {
            allPassengers.add(new Passenger(MyGdxGame.locations));
        }
        passengersWaiting = true;
    }

    private void clearLocations(){
        for(Location location : MyGdxGame.locations.values()){
            location.removePassenger();
        }
    }


    /**
     * The method that listens to all of the input from the user.
     */
    private void listenToInput() {

        if (!(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))) {
            taxi.move(0);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!(taxi.getOrientation()[0] == -1 && taxi.getOrientation()[1] == 0)) {
                taxi.turnLeft();
            }
            taxi.move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (!(taxi.getOrientation()[0] == 1 && taxi.getOrientation()[1] == 0)) {
                taxi.turnRight();

            }
            taxi.move(25);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (!(taxi.getOrientation()[0] == 0 && taxi.getOrientation()[1] == 1)) {
                taxi.turnUp();
            }
            taxi.move(25);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (!(taxi.getOrientation()[0] == 0 && taxi.getOrientation()[1] == -1)) {
                taxi.turnDown();
            }
            taxi.move(25);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            resetPassengers();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            gameSoundPlayer.playCarHorn();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(-1);
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.UP)) && ((Gdx.input.isKeyPressed(Input.Keys.LEFT)) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            playTiresNoise();
        }
    }


    /**
     * Checks for collisions between the car and any buildings on screen. Also ensures that the car cannot drive off the side of the screen.
     * @param velocity An array of float numbers representing the velocity of the car.
     * @return True if a collision occurs, false if one does not.
     */
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

    /**
     * Checks to see if a specific cell on the map is blocked meaning that the car should not be allowed to drive on it.
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     * @param tiledMap The map itself.
     * @return True if the cell is blocked, false if not.
     */
    private static boolean blocked(float x, float y, TiledMap tiledMap) {
        return !isCellProperty(x, y, tiledMap, "road");
    }

    /**
     * Checks the property of a specific cell on the map.
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     * @param tiledMap The map itself.
     * @param property The property to check the cell for.
     * @return True if the cell in question matches the property provided, false if it does not match.
     */
    private static boolean isCellProperty(float x, float y, TiledMap tiledMap, String property) {
        MapLayers allLayers = tiledMap.getLayers();
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) allLayers.get(0);
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return (cell != null) &&  (cell.getTile() != null)  &&  ((cell.getTile().getProperties().containsKey(property)));
    }

    /**
     * Plays tire sound effect.
     */
    private void playTiresNoise() {
        gameSoundPlayer.playTiresNoise();
    }

    /**
     * Plays sound effect of car colliding with a solid object.
     */
    private static void playCollisionNoise() {
        if (Math.abs(taxi.getVelocity()[0] * taxi.getOrientation()[0] + taxi.getVelocity()[1] * taxi.getOrientation()[1]) > 0.7) {
            gameSoundPlayer.playCollisionNoise();
        }
    }


    /**
     * Resets the game to a point at which the car is empty and there is a passenger on screen.
     */
    private void resetPassengers() {
        taxi.empty();
        Array<Passenger> newPassengers = new Array<Passenger>();
        for(Passenger pass : allPassengers){
            pass.getOrigin().removePassenger();
            pass = new Passenger(MyGdxGame.locations);
            newPassengers.add(pass);
        }
        allPassengers = newPassengers;
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
