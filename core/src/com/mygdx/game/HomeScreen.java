package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by zoray on 3/23/16.
 */

public class HomeScreen implements Screen{

    private final MyGdxGame game;
    private Texture background= new Texture(Gdx.files.internal("main_menu_small.png"));
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SIXTY.TTF"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private Skin skin;
    private Stage stage = new Stage();
    private Table buttonTable = new Table();
    private BitmapFont font;


    public HomeScreen(final MyGdxGame game){
        this.game = game;
        Gdx.graphics.setWindowedMode(PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT);
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {

        SpriteBatch batch = new SpriteBatch();
        font = new BitmapFont();
        setUpFont();

        batch.begin();

        batch.draw(background,0,0);
        font.draw(batch, "THE DAILY RIDER!", 20, 660);
        font.draw(batch, "Team Zoframenco", 20, 600);

        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new com.mygdx.game.PlayScreen(game));
            dispose();
        }
    }

    private void createButton() {
        TextButton startGameButton = new TextButton("Let's get started!", skin);
        buttonTable.add(startGameButton);
        buttonTable.row();
        buttonTable.setFillParent(true);
        stage.addActor(buttonTable);
    }

    private void setUpFont() {
        parameter.size = 44;
        parameter.color = Color.YELLOW;
        font = generator.generateFont(parameter);
        parameter.borderWidth = 2;
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
        generator.dispose();
    }
}
