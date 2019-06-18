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

    public GameManager() {
        image = new ImageTile("/GrassTile.png", 8, 8);
        image.setAlpha(true);
        image2 = new Image("/GrassTile.png");
        image2.setAlpha(true);
    }

    @Override
    public void update(GameContainer gc, float dt) {

    }

    float temp = 0;

    @Override
    public void render(GameContainer gc, Renderer r) {


        r.drawImage(image2, gc.getInput().getMouseX() - 14, gc.getInput().getMouseY());
        r.setzDepth(0);

        r.drawImageTile(image, 30, 30, 1, 1);
        r.setzDepth(2);

    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
