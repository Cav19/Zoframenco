/*Credits: Tiledmap implementation instructions and parts of code from
 http://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx
 on 2/27/2016


*/

package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;


import static java.awt.Toolkit.getDefaultToolkit;

public class UberGame extends ApplicationAdapter {


	SpriteBatch batch;
	private Texture taxiImg;
	private OrthographicCamera camera;
	private Car taxi;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;



	@Override
	public void create() {


		batch = new SpriteBatch();
		taxiImg = new Texture("tiny_car_square.png");
		taxi = new Car(taxiImg);
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		taxi.getSprite().setPosition(width / 2, height / 2);
		taxi.setX_pos(width/2);
		taxi.setY_pos(height/2);
		taxi.getSprite().setSize(32, 32);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 2400, 2400);

		tiledMap = new TmxMapLoader().load("map@3March.tmx");
		MapProperties mapProperties= tiledMap.getProperties();
		int mapWidth = mapProperties.get("width", Integer.class);
		int mapHeight = mapProperties.get("height", Integer.class);

		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		camera.update();

	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();


		batch.begin();
		taxi.getSprite().draw(batch);
		batch.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) taxi.turnLeft();
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) taxi.turnRight();
		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			taxi.driveForward();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) taxi.driveBackward();



	}



}
