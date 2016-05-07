package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by zoray on 4/26/16.
 */
public class ScorePanel extends Actor {

    private Table table;

    private BitmapFont font;

    public ScorePanel () {
        setUpScoreFont();
        setUpTable();
    }

    private void setUpTable() {
        table = new Table();
        table.setFillParent(true);

        Label placeLabel = new Label("Highest Scores", new Label.LabelStyle(font, Color.BLACK));
        Label firstSymbol = new Label("Paul Cantrell", new Label.LabelStyle(font, Color.DARK_GRAY));
        Label secondSymbol = new Label("Kofi Annan", new Label.LabelStyle(font, Color.DARK_GRAY));
        Label thirdSymbol = new Label("Brian Rosenberg", new Label.LabelStyle(font, Color.DARK_GRAY));
        Label currentSymbol = new Label("Player", new Label.LabelStyle(font, Color.BLUE));

        Label scoreLabel = new Label("  $", new Label.LabelStyle(font, Color.BLACK));
        Label firstLabel = new Label("  " + String.format("%02d", 3296), new Label.LabelStyle(font, Color.DARK_GRAY));
        Label secondLabel = new Label("  " + String.format("%02d", 2841), new Label.LabelStyle(font, Color.DARK_GRAY));
        Label thirdLabel = new Label("  " + String.format("%02d", 2475), new Label.LabelStyle(font, Color.DARK_GRAY));
        Label currentLabel = new Label("  " + String.format("%02d", MyGdxGame.score), new Label.LabelStyle(font, Color.BLUE));

        table.add(placeLabel).padTop(10);
        table.add(scoreLabel).padTop(10);

        table.row();
        table.add(firstSymbol).padTop(30);
        table.add(firstLabel).padTop(30);

        table.row();
        table.add(secondSymbol).padTop(10);
        table.add(secondLabel).padTop(10);

        table.row();
        table.add(thirdSymbol).padTop(10);
        table.add(thirdLabel).padTop(10);

        table.row();
        table.add(currentSymbol).padTop(30);
        table.add(currentLabel).padTop(30);
    }

    private void setUpScoreFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LiberationMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.LIGHT_GRAY;
        parameter.borderWidth = 0;
        parameter.borderColor = Color.DARK_GRAY;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public Table getTable() {
        return table;
    }
}
