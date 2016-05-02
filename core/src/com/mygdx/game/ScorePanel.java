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

/**
 * Created by zoray on 4/26/16.
 */
public class ScorePanel extends Actor {

    //Scene2D widgets
    private Table table;

    private Label placeLabel;
    private Label firstSymbol;
    private Label secondSymbol;
    private Label thirdSymbol;
    private Label currentSymbol;

    private Label scoreLabel;
    private Label firstLabel;
    private Label secondLabel;
    private Label thirdLabel;
    private Label currentLabel;

    private BitmapFont font;

    private final MyGdxGame game;

    public ScorePanel (MyGdxGame game, SpriteBatch sb, Camera camera) {
        this.game = game;
        setUpHudFont();
        setUpTable();
    }

    private void setUpTable() {
        table = new Table();
        table.setFillParent(true);

        placeLabel = new Label("Top 3", new Label.LabelStyle(font, Color.BLACK));
        firstSymbol = new Label("Paul Cantrell", new Label.LabelStyle(font, Color.BLUE));
        secondSymbol = new Label("Kofi Annan", new Label.LabelStyle(font, Color.DARK_GRAY));
        thirdSymbol = new Label("Brian Rosenberg", new Label.LabelStyle(font, Color.DARK_GRAY));
        currentSymbol = new Label("Player", new Label.LabelStyle(font, Color.DARK_GRAY));

        scoreLabel = new Label("$$$", new Label.LabelStyle(font, Color.BLACK));
        firstLabel = new Label("$" + String.format("%02d", 2896), new Label.LabelStyle(font, Color.DARK_GRAY));
        secondLabel = new Label("$" + String.format("%02d", 2541), new Label.LabelStyle(font, Color.DARK_GRAY));
        thirdLabel = new Label("$" + String.format("%02d", 2175), new Label.LabelStyle(font, Color.DARK_GRAY));
        currentLabel = new Label("$" + String.format("%02d", game.score), new Label.LabelStyle(font, Color.BLUE));

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

        table.row();
        table.add(currentSymbol).padTop(10);
        table.add(currentLabel).padTop(10);
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
}
