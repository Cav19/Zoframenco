package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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


    public PlayScreen(MyGdxGame game){
        this.game = game;
        batch = new SpriteBatch();
        Gdx.graphics.setWindowedMode(V_WIDTH, V_HEIGHT);
        hud = new Hud(game, batch);
        game.camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        gamePort = new FitViewport(V_WIDTH, V_HEIGHT, game.camera);
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
        game.play();
    }
    private void drawHud() {
        if (game.passenger != null && game.taxi.isFull()) {
            hud.updateMessage("Drop me at " + game.passenger.getDestination().toString());
        }
        hud.updateTime(Gdx.graphics.getDeltaTime());
        hud.stage.draw();
    }

    private void setUpScreen(){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        game.camera.update();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
    }

    private void drawGameObjects() {
        drawMap();
        drawCar(game.taxi);
    }

    public static void drawPassenger(Passenger passenger){
        batch.begin();
        passenger.getSprite().draw(batch);
        batch.end();
    }

    private void drawMap() {
        TiledMapRenderer tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledMapRenderer.setView(game.camera);
        tiledMapRenderer.render();
    }

    public void drawCar(Car taxi){
        batch.begin();
        taxi.getSprite().draw(batch);
        batch.end();
        game.camera.update();
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
