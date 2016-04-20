package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by zoray on 3/23/16.
 */

public class HomeScreen implements Screen{

    final MyGdxGame game;
    private Texture background;
    private Sprite sprite;
    private Viewport gamePort;


    public HomeScreen(final MyGdxGame game){
        this.game = game;
        Gdx.graphics.setWindowedMode(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        //gamePort = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, game.camera);

    }

    @Override
    public void show(){
    }

    @Override
    public void render(float delta) {

        //Gdx.gl.glClearColor(250/255f, 236/255f, 129/255f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch = new SpriteBatch();
        game.font = new BitmapFont();
        background = new Texture(Gdx.files.internal("main_menu_small.png"));
        //sprite = new Sprite(background);
        //sprite.setSize(1f,
                //1f * sprite.getHeight() / sprite.getWidth() );
        //game.batch.setProjectionMatrix(game.GameObjects.camera.combined);

        game.batch.begin();

        game.batch.draw(background,0,0);
        game.font.draw(game.batch, "THE DAILY RIDER!", 40, 600);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new com.mygdx.game.PlayScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        //gamePort.update(width, height);

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
