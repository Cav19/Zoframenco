package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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


    //public static TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    float[] decelleration = new float[2];
    boolean gameStarted = false;
    private Texture taxiImg;
    private final SpriteBatch batch;
    //public static OrthographicCamera camera;
    //public Car taxi;



    public PlayScreen(MyGdxGame game){
        Gdx.graphics.setWindowedMode(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        this.game = game;
        hud = new Hud(game, game.batch);
        //camera = new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, game.camera);
        game.camera.setToOrtho(false, MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        //taxi=game.taxi;
        game.tiledMap = new TmxMapLoader().load("map@1April.tmx");
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.play();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.setToOrtho(false, game.V_WIDTH, game.V_HEIGHT);
        game.camera.update();

        drawGameObjects();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public void drawGameObjects() {
        drawMap();
        drawCar(game.V_WIDTH, game.V_HEIGHT, game.taxi);
    }

    private void drawMap() {
        tiledMapRenderer = new OrthogonalTiledMapRenderer(game.tiledMap);
        tiledMapRenderer.setView(game.camera);
        tiledMapRenderer.render();
    }

    public void drawCar(int width, int height, Car taxi){
        taxi.getSprite().setSize( (width / 30),(width / 30));
        batch.begin();
        taxi.getSprite().draw(batch);
        batch.end();
        /*System.out.println("the taxi I am drawing is "+ taxi.toString());
        Texture taxi2texture = new Texture("tiny_car_square.png");
        System.out.println("Velocity of game car is : " +taxi.velociy[0]+" and "+taxi.velociy[1]);
        Car taxi2= new Car(taxi2texture);
        System.out.println("Another taxi would be called: " +taxi2.toString());
        */
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

    }
}
