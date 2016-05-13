package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Francesco Nutricato on 4/24/2016.
 */
public class soundPlayer {

    private Music moneySound = Gdx.audio.newMusic(Gdx.files.internal("sounds/cash_register.mp3"));
    private Music tiresNoise = Gdx.audio.newMusic(Gdx.files.internal("sounds/tiresNoise.mp3"));
    private Music collisionNoise = Gdx.audio.newMusic(Gdx.files.internal("sounds/crash.mp3"));
    private Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/City_Traffic.mp3"));
    private Music carHorn = Gdx.audio.newMusic(Gdx.files.internal("sounds/carhorn.mp3"));
    private Music carDoor = Gdx.audio.newMusic(Gdx.files.internal("sounds/car_door.mp3"));
    private Music taxiWhistle = Gdx.audio.newMusic(Gdx.files.internal("sounds/taxi_whistle.mp3"));

    public soundPlayer() {
    }

    public void playTiresNoise() {
        tiresNoise.play();
    }

    public void playCollisionNoise() {
        collisionNoise.setVolume(75);
        collisionNoise.play();
    }

    public void playBackGroundMusic() {
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume((float) 0.1);
        backgroundMusic.play();
    }

    public void stop() {
        backgroundMusic.stop();
        moneySound.stop();
        tiresNoise.stop();
        carDoor.stop();
        carDoor.stop();
        taxiWhistle.stop();
    }

    public void playMoneySound() {
        moneySound.play();
    }

    public void playCarHorn() {
        carHorn.play();
    }

    public void playCarDoor() {
        carDoor.play();
    }

    public void playTaxiWhistle() {
        taxiWhistle.play();
    }
}
