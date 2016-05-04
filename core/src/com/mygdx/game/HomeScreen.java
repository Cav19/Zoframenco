package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.*;

/**
 * Created by zoray on 3/23/16.
 */

public class HomeScreen implements Screen{


    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int V_WIDTH =1000; //(screenSize.width -1000)/3 + 1000;
    public static final int V_HEIGHT = 1100; //(screenSize.height -1150)/3 +1150;



    private final MyGdxGame game;
    private Viewport homePort;
    private static OrthographicCamera camera;
    private Stage stage;

    private Texture background;

    private Table buttonTable = new Table();
    private TextButton startGameButton;
    private TextButton instructionButton;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    private Skin skin;


    public HomeScreen(final MyGdxGame game){
        this.game = game;
        Gdx.graphics.setWindowedMode(V_WIDTH, V_HEIGHT);
        homePort = new FitViewport(V_WIDTH, V_HEIGHT, camera);

        background = new Texture(Gdx.files.internal("images/main_menu_small.png"));
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SIXTY.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        font = new BitmapFont();
        setUpFont();

        stage = new Stage();
        //createButtonTable();
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {

        SpriteBatch batch = new SpriteBatch();

        batch.begin();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "THE DAILY RIDER!", 20, 660);
        font.draw(batch, "Team Zoframenco", 20, 600);

        batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new com.mygdx.game.InstructionScreen(game));
            dispose();
        }
    }

    private void createButtonTable() {
        startGameButton = new TextButton("Started game", skin);
        buttonTable.add(startGameButton);

        instructionButton = new TextButton("Instructions", skin);
        buttonTable.add(instructionButton);

        buttonTable.row();
        buttonTable.setFillParent(true);
        stage.addActor(buttonTable);
    }

    private void setUpFont() {
        parameter.size = 44;
        parameter.color = Color.YELLOW;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
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
