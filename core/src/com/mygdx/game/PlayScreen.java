package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by zoray on 3/10/16.
 */

public class PlayScreen implements Screen {

    private MyGdxGame game;
    private Viewport gamePort;
    private Hud hud;
    private TiledMapRenderer tiledMapRenderer;
    private final SpriteBatch batch;
    private Music backgroundMusic;


    public PlayScreen(MyGdxGame game){
        this.game = game;
        Gdx.graphics.setWindowedMode(game.V_WIDTH, game.V_HEIGHT);
        hud = new Hud(game, game.batch);
        game.camera.setToOrtho(false, MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, game.camera);
        game.tiledMap = new TmxMapLoader().load("map@17April.tmx");
        batch = new SpriteBatch();
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
        if (game.passenger != null && game.taxi.full) {
            hud.updateMessage("Drop me at " + game.passenger.destination.toString());
        }
        hud.updateTime(Gdx.graphics.getDeltaTime());
        hud.stage.draw();
    }

    private void setUpScreen(){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.camera.setToOrtho(false, game.V_WIDTH, game.V_HEIGHT);
        game.camera.update();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
    }

    private void drawGameObjects() {
        drawMap();
        drawCar( game.taxi);
    }

    private void drawMap() {
        tiledMapRenderer = new OrthogonalTiledMapRenderer(game.tiledMap);
        tiledMapRenderer.setView(game.camera);
        tiledMapRenderer.render();
    }

    private void drawCar( Car taxi){
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
