package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by zoray on 3/23/16.
 */

public class HomeScreen implements Screen{

    final MyGdxGame game;
    private Texture background= new Texture(Gdx.files.internal("main_menu_small.png"));;
    public FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SIXTY.TTF"));
    public FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();


    public HomeScreen(final MyGdxGame game){
        this.game = game;
        Gdx.graphics.setWindowedMode(game.V_WIDTH, game.V_HEIGHT);
        parameter.size = 60;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        // font size 12 pixels

    }

    @Override
    public void show(){
        game.batch = new SpriteBatch();
        game.font = new BitmapFont();
        game.font = generator.generateFont(parameter);
        generator.dispose();

    }

    @Override
    public void render(float delta) {

        game.batch.begin();

        game.batch.draw(background,0,0);
        game.font.draw(game.batch, "THE DAILY RIDER!", (game.V_HEIGHT/2)-275, (game.V_HEIGHT/2)+parameter.size/2);
        game.batch.end();


        //Setting up font size using FreeTypeFontGenerator

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
