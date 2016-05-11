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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by zoray on 4/30/16.
 */

public class InstructionScreen implements Screen {

    private static OrthographicCamera camera;

    private Stage stage;
    private TextButton startButton;
    private TextButton backButton;

    private Texture instruction;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;
    private Skin skin;

    public InstructionScreen(final MyGdxGame game) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        Viewport port = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, camera);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        instruction = new Texture(Gdx.files.internal("instruction.png"));

        font = new BitmapFont();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SIXTY.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        setUpFont();

        createButtonSkin();
        startButton = new TextButton("Back to Home", skin);
        stage.addActor(startButton);
        backButton = new TextButton("Start a game", skin);
        stage.addActor(backButton);

        startButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame.HomeScreen.dispose();
                MyGdxGame.HomeScreen = new com.mygdx.game.HomeScreen(game);
                game.setScreen(MyGdxGame.HomeScreen);
                dispose();
                System.gc();
            }
        });

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame.PlayScreen = new com.mygdx.game.PlayScreen(game);
                game.setScreen(MyGdxGame.PlayScreen);
                dispose();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255 / 255f, 255 / 255f, 255 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = new SpriteBatch();

        batch.begin();
        batch.draw(instruction,
                Gdx.graphics.getWidth()/5,
                Gdx.graphics.getHeight()/6,
                Gdx.graphics.getWidth()*3/5,
                Gdx.graphics.getHeight()*4/5);
        batch.end();

        startButton.setPosition(camera.viewportWidth / 4 - camera.viewportWidth / 10, camera.viewportHeight / 20);
        backButton.setPosition(camera.viewportWidth * 3 / 4 - camera.viewportWidth / 10, camera.viewportHeight / 20);

        stage.act();
        stage.draw();
    }


    private void createButtonSkin() {
        skin = new Skin();

        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 16, Pixmap.Format.RGB888);
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

    private void setUpFont() {
        parameter.size = 32;
        parameter.color = Color.YELLOW;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

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
