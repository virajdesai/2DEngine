package com.virajdesai.game;

import com.virajdesai.engine.AbstractGame;
import com.virajdesai.engine.GameContainer;
import com.virajdesai.engine.Renderer;
import com.virajdesai.engine.audio.SoundClip;
import com.virajdesai.engine.gfx.Image;
import com.virajdesai.engine.gfx.ImageTile;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameManager extends AbstractGame {

    private ImageTile image;
    private SoundClip clip;
    private Image image2;
    private Image light;

    public GameManager() {
        image = new ImageTile("/test.png", 16, 16);

        image2 = new Image("/GrassTile.png");

        clip = new SoundClip("/audio/clap.wav");
        light = new Image("/light.png");
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(gc.getInput().isButtonDown(MouseEvent.BUTTON1))
            clip.play();

    }

    float temp = 0;

    @Override
    public void render(GameContainer gc, Renderer r) {

        for(int x = 0; x < image.getW(); x++) {
            for(int y = 0; y < image.getH(); y++) {
                r.setLightMap(x, y, image.getP()[x + y * image.getW()]);
            }
        }

        r.setzDepth(0);
        r.drawImage(light, gc.getInput().getMouseX() - 14, gc.getInput().getMouseY());


    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
