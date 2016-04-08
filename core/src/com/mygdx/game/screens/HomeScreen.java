package com.mygdx.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

/**
 * Created by zoray on 3/23/16.
 */

public class HomeScreen implements Screen{

    final MyGdxGame game;

    public HomeScreen(final MyGdxGame game){
        this.game = game;
    }

    @Override
    public void show(){
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.setWindowedMode(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);

        Gdx.gl.glClearColor(250/255f, 236/255f, 129/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch = new SpriteBatch();
        game.font = new BitmapFont();
        //game.batch.setProjectionMatrix(game.GameObjects.camera.combined);

        game.batch.begin();

        game.font.draw(game.batch, "The Daily Rider!",40, 40);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

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
