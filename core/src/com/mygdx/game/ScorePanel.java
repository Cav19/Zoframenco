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

    public ScorePanel() {
        setUpScoreFont();
        setUpTable();
    }

    private void setUpTable() {

        table = new Table();
        table.setFillParent(true);
        Label placeLabel = new Label("Highest Scores", new Label.LabelStyle(font, Color.BLACK));
        Label scoreLabel = new Label("  $", new Label.LabelStyle(font, Color.BLACK));
        table.add(placeLabel).padTop(10);
        table.add(scoreLabel).padTop(10);
        table.row();

        Label highSymbol = new Label("High score",new Label.LabelStyle(font, Color.DARK_GRAY) );
        Label highLabel =  new Label("  " + String.format("%02d", MyGdxGame.prefs.getInteger("highScore")), new Label.LabelStyle(font, Color.DARK_GRAY));
        table.add(highSymbol).padTop(10);
        table.add(highLabel).padTop(10);
        table.row();

        Label newSymbol = new Label("Your score",new Label.LabelStyle(font, Color.DARK_GRAY) );
        Label newLabel =  new Label("  " + String.format("%02d", MyGdxGame.score), new Label.LabelStyle(font, Color.DARK_GRAY));
        table.add(newSymbol).padTop(10);
        table.add(newLabel).padTop(10);
        table.row();

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
