package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by zoray on 4/30/16.
 */
public class InstructionScreen implements Screen {

    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Skin skin;
    private Stage stage;
    private TextButton startButton;
    private TextButton backButton;
    private SpriteBatch batch;

    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 1150;

    public InstructionScreen(MyGdxGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(250/255f, 236/255f, 129/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createButtonSkin();
        startButton = new TextButton("Start Game", skin);
        startButton.setPosition(Gdx.graphics.getWidth()/4 - Gdx.graphics.getWidth()/10 , Gdx.graphics.getHeight()/10);
        stage.addActor(startButton);

        backButton = new TextButton("Back to Home", skin);
        backButton.setPosition(Gdx.graphics.getWidth()*3/4 - Gdx.graphics.getWidth()/10 , Gdx.graphics.getHeight()/10);
        stage.addActor(backButton);

        stage.act();
        stage.draw();

        startButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame g = new MyGdxGame();
                g.setScreen(new com.mygdx.game.PlayScreen(game));
                dispose();
            }
        });

        backButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MyGdxGame g = new MyGdxGame();
                g.setScreen(new com.mygdx.game.HomeScreen(game));
                dispose();
            }
        });
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

    private void createButtonSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }
}
