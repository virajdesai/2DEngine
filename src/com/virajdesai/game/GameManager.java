package com.virajdesai.game;

import com.virajdesai.engine.AbstractGame;
import com.virajdesai.engine.GameContainer;
import com.virajdesai.engine.Renderer;
import com.virajdesai.engine.audio.SoundClip;
import com.virajdesai.engine.gfx.Image;
import com.virajdesai.engine.gfx.ImageTile;
import com.virajdesai.engine.gfx.Light;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameManager extends AbstractGame {

    private ImageTile image;
    private SoundClip clip;
    private Image image2;

    private Light light;

    public GameManager() {
        image = new ImageTile("/test.png", 16, 16);

        image2 = new Image("/test2.png");

        clip = new SoundClip("/audio/clap.wav");
        light = new Light(100, 0xffFFE16B);

    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(gc.getInput().isButtonDown(MouseEvent.BUTTON1))
            clip.play();

    }

    float temp = 0;

    @Override
    public void render(GameContainer gc, Renderer r) {

        r.drawLight(light, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        r.drawImage(image2,0, 0);
        r.drawImageTile(image, 100, 100, 4, 2);



    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
