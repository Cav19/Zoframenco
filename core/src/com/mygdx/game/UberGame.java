/*Credits: Tiledmap implementation instructions and parts of code from
 http://www.gamefromscratch.com/post/2014/04/16/LibGDX-Tutorial-11-Tiled-Maps-Part-1-Simple-Orthogonal-Maps.aspx
 on 2/27/2016


CURRENT COMMANDS: SPACE BAR TO ACCELLERATE,
                 ARROWS TO TURN THE CAR IN THE DIRECTION OF THE ARROW PRESSED

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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


import java.awt.*;


public class UberGame extends ApplicationAdapter {


    SpriteBatch batch;
    private Texture taxiImg;
    private OrthographicCamera camera;
    private Car taxi;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    //public float width = (float)screenSize.getWidth();
    //public float height =(float)screenSize.getHeight();
    public float width = 1000;
    public float height = 1000;
    float[] decelleration= new float[2];


    @Override
    public void create() {

        setUpScreen();
        createCar();

    }

    @Override
    public void render() {
        drawGameObjects();
        play();

    }


    public void setUpScreen() {
        Gdx.graphics.setWindowedMode((int) width, (int) height);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        tiledMap = new TmxMapLoader().load("map@9March.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera.update();
    }


    public void createCar() {
        taxiImg = new Texture("tiny_car_square.png");
        taxi = new Car(taxiImg, this.camera);
        taxi.getSprite().setPosition((int) (width / 2), (int) (height / 2));
        taxi.X_pos = (int) (width / 2);
        taxi.Y_pos = (int) (height / 2);
        taxi.getSprite().setSize((int) (width / 25), (int) (width / 25));
        taxi.setOrientation(0, 1);
    }


    public void drawGameObjects() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.begin();
        taxi.getSprite().draw(batch);
        batch.end();
    }

    public void applyFriction(float[] decelleration){
        System.out.println(Gdx.graphics.getDeltaTime());
        taxi.velociy[0] -=  taxi.velociy[0]*0.015;//decelleration[0];
        taxi.velociy[1] -= taxi.velociy[1] *0.015;//decelleration[1];
        taxi.driveForward(tiledMap);
    }

    public void play() {
        System.out.println(taxi.velociy[0] + " and " + taxi.velociy[1]);


        if (!(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))){
            applyFriction(decelleration);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!(taxi.orientation[0] == -1 && taxi.orientation[1] == 0)) {
                taxi.turnLeft(tiledMap);
            } /*else {
                taxi.accellerate(tiledMap, Gdx.graphics.getDeltaTime());
                taxi.driveForward(tiledMap);
            }*/
            move();
        }


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (!(taxi.orientation[0] == 1 && taxi.orientation[1] == 0)) {
                taxi.turnRight(tiledMap);
            }
            move();
                /*else {
                    taxi.accellerate(tiledMap, Gdx.graphics.getDeltaTime());
                    taxi.driveForward(tiledMap);
                }*/
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (!(taxi.orientation[0] == 0 && taxi.orientation[1] == 1)) {
                taxi.turnUp(tiledMap);
            }
            move();

               /* else {
                taxi.accellerate(tiledMap, Gdx.graphics.getDeltaTime());
                taxi.driveForward(tiledMap);
            }
            */

        }


        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (!(taxi.orientation[0] == 0 && taxi.orientation[1] == -1)) {
                taxi.turnDown(tiledMap);
            }
            move();
                    /*else
                    taxi.accellerate(tiledMap, Gdx.graphics.getDeltaTime());
                    taxi.driveForward(tiledMap);
            */
        }


        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            taxi.restart(tiledMap);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            System.exit(-1);
        }
    }




    public void move(){
        taxi.accellerate(tiledMap, 25);
        decelleration[0]= (float)(taxi.velociy[0] * 0.1);
        decelleration[1]= (float)(taxi.velociy[1] * 0.1);
        applyFriction(decelleration);
        taxi.driveForward(tiledMap);
    }

    }




