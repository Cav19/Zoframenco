package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by zoray on 4/25/16.
 */
public class EndScreen implements Screen{

    private final int V_WIDTH = HomeScreen.V_WIDTH;
    private final int V_HEIGHT = HomeScreen.V_HEIGHT;

    private final MyGdxGame game;
    private OrthographicCamera camera;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    private Skin skin;

    private Stage stage;
    private TextButton restartButton;
    private SpriteBatch batch;
    private ScorePanel scores;
    private Table scoreTable;
    private Viewport scorePort;


    public EndScreen(final MyGdxGame game) {
        this.game = game;

        camera = new OrthographicCamera(HomeScreen.V_WIDTH, HomeScreen.V_HEIGHT);
        camera.setToOrtho(false,HomeScreen.V_WIDTH, HomeScreen.V_HEIGHT);
        scorePort = new FitViewport(HomeScreen.V_WIDTH, HomeScreen.V_HEIGHT, camera);
        scores = new ScorePanel(game);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SIXTY.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        setUpFont();
        createButtonSkin();
        restartButton = new TextButton("Restart", skin);
        stage.addActor(restartButton);

        restartButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame.worldTimer = 90;
                MyGdxGame.score = 0;
                game.setScreen(new com.mygdx.game.HomeScreen(game));
                dispose();
            }
        });

        scoreTable = scores.getTable();
        stage.addActor(scores.getTable());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(250/255f, 236/255f, 129/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        restartButton.setPosition(camera.viewportWidth/2 - camera.viewportWidth/10, camera.viewportHeight/16);

        stage.act();
        stage.draw();
    }

    private void setUpFont() {
        parameter.size = 44;
        parameter.color = Color.YELLOW;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
    }

    private void createButtonSkin(){
        skin = new Skin();

        Pixmap pixmap = new Pixmap((int)camera.viewportWidth/4, (int)camera.viewportHeight/16, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = font;
        skin.add("default", textButtonStyle);
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
