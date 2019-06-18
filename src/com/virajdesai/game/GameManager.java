package com.virajdesai.game;

import com.virajdesai.engine.AbstractGame;
import com.virajdesai.engine.GameContainer;
import com.virajdesai.engine.Renderer;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    public GameManager() {

    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(gc.getInput().isKeyDown(KeyEvent.VK_A))
            System.out.println("A was pressed");
    }

    @Override
    public void render(GameContainer gc, Renderer r) {

    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
