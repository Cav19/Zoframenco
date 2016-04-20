package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

        //Setting up font size using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SIXTY.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.BLACK;
        game.font = generator.generateFont(parameter); // font size 12 pixels
        //parameter.borderColor = Color.BLACK;
        //parameter.Color = Color.BLUE;
        parameter.borderWidth = 3;
        generator.dispose();

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
