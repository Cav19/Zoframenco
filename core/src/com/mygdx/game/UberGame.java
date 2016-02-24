package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UberGame extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture taxiImg;
	private Texture map;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new Texture("map.jpg");
		taxiImg = new Texture("taxi.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(map, 0, 0);
		batch.draw(taxiImg, 0, 0);
		batch.end();
	}
}
