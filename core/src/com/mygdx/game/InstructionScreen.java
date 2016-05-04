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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by zoray on 4/30/16.
 */
public class InstructionScreen implements Screen {

    private final MyGdxGame game;
    private static OrthographicCamera camera;
    private Skin skin;
    private Stage stage;
    private TextButton startButton;
    private TextButton backButton;
    private SpriteBatch batch;
    private Viewport port;


    private Texture instruction= new Texture(Gdx.files.internal("instruction_small.png"));

    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 1150;

    public InstructionScreen(MyGdxGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        port = new FitViewport(V_WIDTH, V_HEIGHT, camera);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createButtonSkin();
        startButton = new TextButton("Start Game", skin);
        stage.addActor(startButton);
        backButton = new TextButton("Back to Home", skin);
        stage.addActor(backButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255/255f, 255/255f, 255/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = new SpriteBatch();

        batch.begin();
        batch.draw(instruction, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        startButton.setPosition(Gdx.graphics.getWidth()/4 - Gdx.graphics.getWidth()/10 , Gdx.graphics.getHeight()/10);
        backButton.setPosition(Gdx.graphics.getWidth()*3/4 - Gdx.graphics.getWidth()/10 , Gdx.graphics.getHeight()/10);

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

//        if (Gdx.input.isTouched()) {
//            game.setScreen(new com.mygdx.game.PlayScreen(game));
//            dispose();
//        }
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
