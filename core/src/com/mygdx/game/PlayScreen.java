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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by zoray on 3/10/16.
 */

public class PlayScreen implements Screen {

    private static MyGdxGame game;
    private Viewport gamePort;
    private Hud hud;
    private static TiledMap tiledMap;
    public static SpriteBatch batch;
    private static OrthographicCamera camera;
    private static Car taxi = new Car();
    private Array<Passenger> allPassengers = new Array<Passenger>();
    public static soundPlayer gameSoundPlayer;

    private long timeOfLastPassenger;
    private long spawnTime;
    private Timer timer = new Timer();
    private Coin coin = new Coin();
    String inputKey = "";
    private float pulseTime = 0;

    public PlayScreen(MyGdxGame game) {
        PlayScreen.game = game;
        batch = new SpriteBatch();
        Gdx.graphics.setWindowedMode(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        camera = new OrthographicCamera();
        hud = new Hud(batch, camera);
        camera.setToOrtho(false, MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, camera);
        tiledMap = new TmxMapLoader().load("map_assets/map@17April.tmx");
        gameSoundPlayer = new soundPlayer();
        allPassengers.add(new Passenger());
        spawnTime = setNextSpawnTime();
        timeOfLastPassenger = TimeUtils.millis();
    }

    /**
     * Check if the taxi is at the same position as timer
     */
    public boolean isTaxiAtTimer() {
        return timer.isVisible()
                && Math.hypot(taxi.getX() - timer.getX(), taxi.getY() - timer.getY()) < 40;
    }

    /**
     * Check if the taxi is at the same position as coin
     */
    public boolean isTaxiAtCoin() {
        return coin.isVisible()
                && Math.hypot(taxi.getX() - coin.getX(), taxi.getY() - coin.getY()) < 40;
    }

    /**
     * Check if coin is overlapping with timer
     */
    public boolean isCoinTimerOverlapping() {
        return coin.isVisible()
                && timer.isVisible()
                && Math.hypot(timer.getX() - coin.getX(), timer.getY() - coin.getY()) < 40;
    }

    @Override
    public void show() {
        playBackGroundMusic();
        setUpScreen();

    }

    /**
     * Plays background sound effect.
     */
    private void playBackGroundMusic() {
        gameSoundPlayer.playBackGroundMusic();
    }

    /**
     * Renders the game objects onto the screen.
     *
     * @param delta The amount of time passed since the previous render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl20.glLineWidth(2f);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawMap();
        drawCar(taxi);
        drawHud();
        play();

        if (taxi.isFull() && timer.isVisible() && coin.isVisible()) {
            drawTimer(timer);
            drawCoin(coin);
        }
        if (taxi.isFull()) {
            highlightDestination(taxi.getPassenger().getDestination(), delta);
        }

        for (Passenger pass : allPassengers) {
            drawPassenger(pass);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            endGame();
        }
        if (MyGdxGame.worldTimer <= 0) {
            endGame();
        }

    }


    private void endGame() {
        allPassengers.clear();
        taxi.empty();
        taxi.setPosition(Car.InitialPosition[0], Car.InitialPosition[1]);
        game.EndScreen= new com.mygdx.game.EndScreen(game);
        game.setScreen(game.EndScreen);
        game.PlayScreen.dispose();
        gameSoundPlayer.playCarHorn();
        gameSoundPlayer.stop();
        dispose();
        System.gc();
    }

    /**
     * Draws the hud at the bottom of the screen.
     */
    private void drawHud() {
        if (taxi.isFull()) {
            hud.updateMessage("Drop me at " + taxi.getPassenger().getDestination().getName() + "!");
        } else {
            hud.updateMessage("Go pick up a passenger!");
        }
        Hud.updateTime(Gdx.graphics.getDeltaTime());
        hud.updateScore();
        Hud.stage.draw();
    }

    /**
     * Sets up the main game screen and initiates the camera's view.
     */
    private void setUpScreen() {

        camera.setToOrtho(false, MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        camera.update();
        batch.setProjectionMatrix(Hud.stage.getCamera().combined);
    }

    /**
     * Draws a passenger onto the screen at their designated location.
     *
     * @param passenger The instance of passenger to be drawn.
     */
    private void drawPassenger(Passenger passenger) {
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
     *
     * @param taxi The instance of car to be drawn.
     */
    private void drawCar(Car taxi) {
        batch.begin();
        taxi.getSprite().draw(batch);
        batch.end();
    }

    /**
     * Draws a timer onto the screen at their designated location.
     *
     * @param timer The instance of timer to be drawn.
     */
    private void drawTimer(Timer timer) {
        batch.begin();
        timer.getSprite().draw(batch);
        batch.end();
    }

    /**
     * Draws a coin onto the screen at their designated location.
     *
     * @param coin The instance of timer to be drawn.
     */
    private void drawCoin(Coin coin) {
        batch.begin();
        coin.getSprite().draw(batch);
        batch.end();
    }

    /**
     * Highlights the destination of the passenger currently in the user's car.
     *
     * @param destination The destination on the map to be highlighted.
     */
    private void highlightDestination(Location destination, float delta) {
        pulseTime += delta * 60;
        float pulse = (2.8f * MathUtils.cos(pulseTime / (2 * MathUtils.PI))) + 2;
        Gdx.gl20.glLineWidth(5f + pulse / 2);
        Rectangle box = destination.getRectangle();
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(camera.combined);
        renderer.updateMatrices();
        renderer.setColor(Color.RED);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(box.getX() - pulse, box.getY() - pulse, box.getWidth() + 2 * pulse, box.getHeight() + 2 * pulse);
        renderer.end();
    }


    /**
     * The main play function of the game which controls the game flow and individual game states.
     */
    private void play() {
        long timeSinceLastPassenger = TimeUtils.timeSinceMillis(timeOfLastPassenger);
        listenToInput();



        /**
         * Spawns a new passenger and a coin if the time since the last passenger has exceeded the designated spawn timer.
         */
        if (timeSinceLastPassenger >= spawnTime && allPassengers.size < 18) {
            spawnPassenger();
            timeOfLastPassenger = TimeUtils.millis();
            spawnTime = setNextSpawnTime();
        }

        /**
         * Game State: Taxi is empty and is driving around, looking for passengers.
         * Checks to see if the taxi has arrived at a passenger and makes the taxi pick up that passenger if it has.
         */
        for (int i = 0; i < allPassengers.size; i++) {
            if (taxi.hasArrived(allPassengers.get(i).getOrigin()) && !taxi.isFull()) {
                taxi.addPassenger(allPassengers.get(i));
                allPassengers.removeIndex(i);
                gameSoundPlayer.playCarDoor();
            }
        }

        /**
         * Game State: Taxi is full and is driving towards destination.
         * Highlights the target destination and unloads the passenger from the taxi upon arrival.
         */
        if (taxi.isFull()) {
            if (taxi.hasArrived(taxi.getPassenger().getDestination())) {
                do{
                    timer.randomlyPlaceTimer();
                    coin.randomlyPlaceCoin();
                } while(isCoinTimerOverlapping());
                gameSoundPlayer.playMoneySound();
                game.addScore(taxi.getPassenger().getFare());
                hud.updateScore();
                taxi.empty();
            }
            if (isTaxiAtTimer()) {
                MyGdxGame.worldTimer += 6;
                Hud.updateTime(6);
                timer.removeTimer();
            }
            if (isTaxiAtCoin()) {
                MyGdxGame.score += 20;
                coin.removeCoin();
            }
        }
    }

    /**
     * Sets the spawn time for the next passenger.
     *
     * @return The spawn time of the next passenger in milliseconds.
     */
    private long setNextSpawnTime() {
        return MathUtils.random(4000, 10000);
    }

    /**
     * Spawns a new passenger inside the game.
     */
    private void spawnPassenger() {
        allPassengers.add(new Passenger());
        gameSoundPlayer.playTaxiWhistle();
    }


    /**
     * The method that listens to all of the input from the user.
     */




    private void updateInputKey() {
        System.out.println(Gdx.input.getGyroscopeX());
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
        ) {
            inputKey = "LEFT";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            inputKey = "RIGHT";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            inputKey = "UP";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            inputKey = "DOWN";
        }
    }


    private void listenToInput() {


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gameSoundPlayer.playCarHorn();
        }


        if (!(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))) {
            taxi.move(0);
        }

        updateInputKey();

        if ((taxi.currentAngle != taxi.getDirection(inputKey).angle) && !inputKey.isEmpty()) {
            taxi.turn(inputKey);

       } else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.UP)) && ((Gdx.input.isKeyPressed(Input.Keys.LEFT)) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
            taxi.move(1);
            playTiresNoise();
        } else if ((taxi.currentAngle == taxi.getDirection(inputKey).angle) && (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))) {
            taxi.setOrientation(taxi.getDirection(inputKey).x, taxi.getDirection(inputKey).y);
            taxi.move(15);
        }

    }


    /**
     * Checks for collisions between the car and any buildings on screen. Also ensures that the car cannot drive off the side of the screen.
     *
     * @return True if a collision occurs, false if one does not.
     */
    public static boolean checkCarCollisions() {
        updatePosition();
        return checkMapBoundaries() || checkCollisionPoints();
    }

    private static void updatePosition(){
        taxi.setX(taxi.getX() + taxi.getVelocity()[0]);
        taxi.setY(taxi.getY() + taxi.getVelocity()[1]);
    }


    private static boolean checkMapBoundaries() {

        if ((taxi.getX() + (int) taxi.getSprite().getWidth() >= MyGdxGame.V_WIDTH) ||      //right edge
                (taxi.getX() <= 0) ||                                                    //left edge
                (taxi.getY() + (int) taxi.getSprite().getWidth() >= MyGdxGame.V_HEIGHT) ||    //top edge
                (taxi.getY() <= 0)                                                       //bottom edge
                ) {
            return true;
        } else return false;
    }

    private static boolean checkCollisionPoints() {
        try {
            return (!isCellProperty((float) (taxi.getX() + 0.65 * taxi.getSprite().getWidth()), taxi.getY() + taxi.getSprite().getHeight() / 2, "road")
                    || !isCellProperty((float) (taxi.getX() + 0.35 * taxi.getSprite().getWidth()), taxi.getY() + taxi.getSprite().getHeight() / 2, "road")
                    || !isCellProperty(taxi.getX() + taxi.getSprite().getWidth() / 2, (float) (taxi.getY() + 0.65 * taxi.getSprite().getHeight()), "road"))
                    || !isCellProperty(taxi.getX() + taxi.getSprite().getWidth() / 2, (float) (taxi.getY() + 0.35 * taxi.getSprite().getHeight()), "road");
        } catch (NullPointerException e) {
            return true;
        }
    }


    /**
     * Checks the property of a specific cell on the map.
     *
     * @param x        The x coordinate of the cell.
     * @param y        The y coordinate of the cell.
     * @param property The property to check the cell for.
     * @return True if the cell in question matches the property provided, false if it does not match.
     */

    public static boolean isCellProperty(float x, float y, String property) {

        MapLayers allLayers = tiledMap.getLayers();
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) allLayers.get(0);
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        try {
            return ((cell.getTile().getProperties().containsKey(property)));
        } catch (NullPointerException e) {
            return false;
        }
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
    public static void playCollisionNoise() {
        if (Math.abs(taxi.getVelocity()[0] * taxi.getOrientation()[0] + taxi.getVelocity()[1] * taxi.getOrientation()[1]) > 0.7) {
            gameSoundPlayer.playCollisionNoise();
        }
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
        //batch.dispose();
    }

}
