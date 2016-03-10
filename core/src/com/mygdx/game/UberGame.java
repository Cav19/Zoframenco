/*Credits: Tiledmap implementation instructions and parts of code from
 http://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx
 on 2/27/2016


*/

package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.intellij.ide.actions.ToggleFullScreenAction;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vcs.Ring;
import com.intellij.psi.compiled.ClassFileDecompilers;


import java.awt.*;

import static java.awt.Toolkit.getDefaultToolkit;


public class UberGame extends ApplicationAdapter {


	SpriteBatch batch;
	private Texture taxiImg;
	private OrthographicCamera camera;
	private Car taxi;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	Dimension screenSize =  Toolkit.getDefaultToolkit().getScreenSize();
	//public float width = (float)screenSize.getWidth();
	//public float height =(float)screenSize.getHeight();
	public float width=1000;
	public float height=1000;








	@Override
	public void create() {

		Gdx.graphics.setWindowedMode((int)width, (int)height);
		batch = new SpriteBatch();
		taxiImg = new Texture("tiny_car_square.png");
		taxi = new Car(taxiImg, this.camera);
		taxi.getSprite().setPosition((int)(width/2), (int)(height / 2));
		taxi.setX_pos((int)(width/2));
		taxi.setY_pos((int)(height / 2));
		taxi.getSprite().setSize((int)(width/25), (int)(width/25));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		tiledMap = new TmxMapLoader().load("map@9March.tmx");

		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		camera.update();




	}


	@Override
	public void render() {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();


		batch.begin();
		taxi.getSprite().draw(batch);
		batch.end();


		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) taxi.turnLeft(tiledMap);
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) taxi.turnRight(tiledMap);
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			taxi.driveForward(tiledMap, 200);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) taxi.driveBackward(tiledMap, 100);
		if (Gdx.input.isKeyPressed(Input.Keys.R)) {
			taxi.restart(tiledMap);
		}


	}




}



