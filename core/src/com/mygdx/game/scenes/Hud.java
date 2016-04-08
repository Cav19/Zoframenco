package com.mygdx.game.scenes;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGdxGame;

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
    private static Integer score;

    //Scene2D widgets
    private static Label scoreLabel;
    private Label timeLabel;
    private Label countdownLabel;
    private Label gameNameLabel;
    private Label groupNameLabel;
    private Label scoreTextLabel;

    private Label messageLabel;
    private Label messageTextLabel;

    public Hud(MyGdxGame game, SpriteBatch sb) {
        worldTimer = 100;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, game.camera);
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        gameNameLabel = new Label("CrazyUber", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        groupNameLabel = new Label("Zoframenco", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        scoreTextLabel = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%02d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        messageTextLabel = new Label("Message: ", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        messageLabel = new Label("Yo! Welcome to CrazyUber!", new Label.LabelStyle(new BitmapFont(), Color.GREEN));

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
    }

    public void updateMessage(String msg){
        messageLabel.setText(msg);
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%02d", score));
    }

}
