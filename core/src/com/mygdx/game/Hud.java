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
    private Viewport viewport;

    //score/time tracking variables
    private Integer worldTimer;
    private float timeCount;

    //Scene2D widgets
    private static Label scoreLabel;
    private Label timeLabel;
    private Label countdownLabel;
    private Label gameNameLabel;
    private Label groupNameLabel;
    private Label scoreTextLabel;

    private Label messageLabel;
    private Label messageTextLabel;

    private final MyGdxGame game;

    public BitmapFont font;

    public static Json Scores = new Json();

    public Hud(MyGdxGame game, SpriteBatch sb, Camera camera) {
        worldTimer = 100;
        timeCount = 0;
        game.score = 0;

        viewport = new FitViewport(PlayScreen.V_WIDTH, PlayScreen.V_HEIGHT, camera);
        stage = new Stage(viewport, sb);

        this.game = game;

        setUpHudFont();

        setUpLabels();
    }

    private void setUpHudFont() {
        //Setting up font size using FreeTypeFontGenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LiberationMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        font = generator.generateFont(parameter); // font size 12 pixels
        parameter.borderWidth = 3;
        generator.dispose();
    }

    private void setUpLabels() {
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font, Color.BLACK));  // Color.WHITE
        timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.BLACK));

        gameNameLabel = new Label("The Daily Rider", new Label.LabelStyle(font, Color.BLACK));
        groupNameLabel = new Label("Zoframenco", new Label.LabelStyle(font, Color.BLACK));

        scoreTextLabel = new Label("Score", new Label.LabelStyle(font, Color.BLACK));
        scoreLabel = new Label("$" + String.format("%02d", game.score), new Label.LabelStyle(font, Color.BLACK));

        messageTextLabel = new Label("Message: ", new Label.LabelStyle(font, Color.BLACK));
        messageLabel = new Label("Yo! Welcome to The Daily Rider!", new Label.LabelStyle(font, Color.BLUE));

        table.add(gameNameLabel).expandX().padTop(10);
        table.add(scoreTextLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(groupNameLabel).expandX();
        table.add(scoreLabel).expandX();
        table.add(countdownLabel).expandX();

        table.row();
        table.add(messageTextLabel).expandX();
        table.add(messageLabel).expandX();

        stage.addActor(table);
    }

    public void updateTime(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.valueOf(worldTimer));
            timeCount = 0;
        }
        if (worldTimer<=0){
            System.out.println("GAME OVER");
            System.out.println("FINAL SCORE: "+ game.score);
            stage.dispose();
            System.out.println(Scores.toJson(game.score));
            game.setScreen(new com.mygdx.game.EndScreen(game));
        }
    }

    public String getHighScores(){
        Scores.prettyPrint(game.score);
        String HighScores= Scores.prettyPrint(game.score);
        return HighScores;
    }

    public int getTime(){
        return this.worldTimer;
    }

    public void updateMessage(String msg){
        messageLabel.setText(msg);
    }

    public void updateScore (){
        scoreLabel.setText("$" + String.format("%02d", game.score));
    }

}
