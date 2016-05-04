package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by zoray on 3/8/16.
 * Reference: https://www.youtube.com/watch?v=gqxkeKaw1MY
 */

public class Hud {
    //stage and viewport
    public Stage stage;

    //score/time tracking variables
    private float timeCount;

    //Scene2D widgets
    private static Label scoreLabel;
    private Label countdownLabel;

    private Label messageLabel;

    private final MyGdxGame game;

    public BitmapFont font;

    public static Json Scores = new Json();

    public Hud(MyGdxGame game, SpriteBatch sb, Camera camera) {
        MyGdxGame.worldTimer = 100;

        timeCount = 0;
        MyGdxGame.score = 0;


        Viewport viewport = new FitViewport(HomeScreen.V_WIDTH, HomeScreen.V_HEIGHT, camera);
        stage = new Stage(viewport, sb);

        this.game = game;

        setUpHudFont();

        setUpLabels();
    }

    private void setUpHudFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LiberationMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
    }

    private void setUpLabels() {
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", MyGdxGame.worldTimer), new Label.LabelStyle(font, Color.BLACK));  // Color.WHITE
        Label timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.BLACK));

        Label exitLabel = new Label("press ESC to exit", new Label.LabelStyle(font, Color.MAGENTA));

        Label scoreTextLabel = new Label("Score", new Label.LabelStyle(font, Color.BLACK));
        scoreLabel = new Label("$" + String.format("%02d", MyGdxGame.score), new Label.LabelStyle(font, Color.BLACK));

        Label messageTextLabel = new Label("Message: ", new Label.LabelStyle(font, Color.BLACK));
        messageLabel = new Label("Yo! Welcome to The Daily Rider!", new Label.LabelStyle(font, Color.BLUE));

        Label blankLabel = new Label("", new Label.LabelStyle(font, Color.BLACK));

        table.add(timeLabel).expandX().padTop(-100);
        table.add(messageTextLabel).expandX().right().padTop(-100);
        table.add(messageLabel).expandX().left().padTop(-100);
        table.add(scoreTextLabel).expandX().padTop(-100);

        table.row();
        table.add(countdownLabel).expandX().padTop(-50);
        table.add(blankLabel).expandX();
        table.add(exitLabel).expandX().padTop(-50);
        table.add(scoreLabel).expandX().padTop(-50);

        stage.addActor(table);
    }

    public void updateTime(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            MyGdxGame.worldTimer--;
            countdownLabel.setText(String.valueOf(MyGdxGame.worldTimer));
            timeCount = 0;
        }
        if (MyGdxGame.worldTimer <=0){
            System.out.println("GAME OVER");
            System.out.println("FINAL SCORE: "+ MyGdxGame.score);
            stage.dispose();
        }
    }

    public String getHighScores(){
        Scores.prettyPrint(MyGdxGame.score);
        String HighScores= Scores.prettyPrint(MyGdxGame.score);
        return HighScores;
    }

    public int getTime(){
        return MyGdxGame.worldTimer;
    }

    public void updateMessage(String msg){
        messageLabel.setText(msg);
    }

    public void updateScore (){
        scoreLabel.setText("$" + String.format("%02d", MyGdxGame.score));
    }

}
