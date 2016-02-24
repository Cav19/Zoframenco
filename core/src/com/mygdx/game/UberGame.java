package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class UberGame extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture taxiImg;
	private Texture mapImg;
	private OrthographicCamera camera;
	private Rectangle car;
	private Rectangle map;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		car = new Rectangle();
		car.x = 800 / 2;
		car.y = 480 / 2;
		car.width = 64;
		car.height = 64;
		taxiImg = new Texture("taxi.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		mapImg = new Texture("map.jpg");

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(mapImg, 0, 0);
		batch.draw(taxiImg, car.x, car.y);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) car.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) car.x += 200 * Gdx.graphics.getDeltaTime();

		if(car.x < 0) car.x = 0;
		if(car.x > 800 - 64) car.x = 800 - 64;
	}
}
