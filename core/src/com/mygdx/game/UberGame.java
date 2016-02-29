/*Credits: Tiledmap implementation instructions and parts of code from
 http://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx
 on 2/27/2016


*/

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class UberGame extends ApplicationAdapter {



	SpriteBatch batch;
	private Texture taxiImg;
	private OrthographicCamera camera;
	private Rectangle car;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	@Override
	public void create () {


		batch = new SpriteBatch();
		car = new Rectangle();
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		car.x = width/2;
		car.y = height/2;
		car.width = 28;
		car.height = 64;
		taxiImg = new Texture("tiny_car.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);

		tiledMap = new TmxMapLoader().load("tmpRoad.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		camera.update();

	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();


		batch.begin();
		batch.draw(taxiImg, car.x, car.y);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) car.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) car.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) car.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) car.y -= 200 * Gdx.graphics.getDeltaTime();


		if(car.x < 0) car.x = 0;
		if(car.x > Gdx.graphics.getWidth() - car.width) car.x = Gdx.graphics.getWidth() - car.width;

		if(car.y < 0) car.y = 0;
		if(car.y > Gdx.graphics.getHeight() - car.height) car.y = Gdx.graphics.getHeight() - car.height;
	}
}