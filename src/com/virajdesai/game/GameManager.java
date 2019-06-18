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

    public GameManager() {
        image = new ImageTile("/test.png", 16, 16);
        clip = new SoundClip("/audio/clap.wav");
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(gc.getInput().isButtonDown(MouseEvent.BUTTON1))
            clip.play();

        temp += dt * 2;

        if(temp > 6) {
            temp = 0;
        }
    }

    float temp = 0;

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImageTile(image, gc.getInput().getMouseX() - 14, gc.getInput().getMouseY() - 16, (int)temp, 2);
        r.drawText("engine text renderer test", 0, 0 , -1);
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
