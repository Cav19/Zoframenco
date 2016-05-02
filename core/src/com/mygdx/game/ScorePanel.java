package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.HashMap;

/**
 * Created by zoray on 4/26/16.
 */
public class ScorePanel extends Actor {

    //score tracking variables
    private static HashMap<Integer, Integer> scores;

    //Scene2D widgets
    private Table table;

    private Label placeLabel;
    private Label firstSymbol;
    private Label secondSymbol;
    private Label thirdSymbol;

    private Label scoreLabel;
    private Label firstLabel;
    private Label secondLabel;
    private Label thirdLabel;

    public BitmapFont font;

    public ScorePanel (MyGdxGame game, SpriteBatch sb, Camera camera) {
        scores = new HashMap<Integer, Integer>();
        scores.put(1, 0);
        scores.put(2, 0);
        scores.put(3, 0);

        setUpHudFont();
        setUpTable();
    }

    private void setUpTable() {
        table = new Table();
        //table.top();
        table.setFillParent(true);


        placeLabel = new Label("Rank", new Label.LabelStyle(font, Color.BLACK));
        firstSymbol = new Label("#1", new Label.LabelStyle(font, Color.BLUE));
        secondSymbol = new Label("#2", new Label.LabelStyle(font, Color.DARK_GRAY));
        thirdSymbol = new Label("#3 ", new Label.LabelStyle(font, Color.DARK_GRAY));

        scoreLabel = new Label("$$$", new Label.LabelStyle(font, Color.BLACK));
        firstLabel = new Label("$" + String.format("%02d", scores.get(1)), new Label.LabelStyle(font, Color.BLUE));
        secondLabel = new Label("$" + String.format("%02d", scores.get(2)), new Label.LabelStyle(font, Color.DARK_GRAY));
        thirdLabel = new Label("$" + String.format("%02d", scores.get(3)), new Label.LabelStyle(font, Color.DARK_GRAY));

        table.add(placeLabel).padTop(10);
        table.add(scoreLabel).padTop(10);

        table.row();
        table.add(firstSymbol).padTop(10);
        table.add(firstLabel).padTop(10);

        table.row();
        table.add(secondSymbol).padTop(10);
        table.add(secondLabel).padTop(10);

        table.row();
        table.add(thirdSymbol).padTop(10);
        table.add(thirdLabel).padTop(10);
    }

    private void setUpHudFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LiberationMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        font = generator.generateFont(parameter);
        parameter.borderWidth = 3;
        generator.dispose();
    }

    public Table getTable() {
        return table;
    }

    public static HashMap<Integer, Integer> getScores() {
        return scores;
    }

    public static void setScores(HashMap<Integer, Integer> scores) {
        ScorePanel.scores = scores;
    }
}
