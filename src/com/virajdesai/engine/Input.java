package com.virajdesai.engine;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, FocusListener {

    private GameContainer gc;

    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];

    private final int NUM_BUTTONS = 5;
    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private int mouseX, mouseY;
    private int scroll;

    public Input(GameContainer gc) {

        this.gc = gc;

        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
        gc.getWindow().getCanvas().addMouseWheelListener(this);
    }

    public void update() {
        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
        System.arraycopy(buttons, 0, buttonsLast, 0, NUM_BUTTONS);

        scroll = 0;
    }

    public void mousePressed(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() < NUM_BUTTONS) {
            buttons[mouseEvent.getButton()] = true;
        }
    }
    public void mouseReleased(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() < NUM_BUTTONS) {
            buttons[mouseEvent.getButton()] = false;
        }
    }
    public void mouseDragged(MouseEvent e) {
        mouseX = (int)(e.getX() / gc.getScale());
        mouseY = (int)(e.getY() / gc.getScale());
    }
    public void mouseMoved(MouseEvent e) {
        mouseX = (int)(e.getX() / gc.getScale());
        mouseY = (int)(e.getY() / gc.getScale());
    }
    public void mouseWheelMoved(MouseWheelEvent e) { scroll = e.getWheelRotation(); }
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() < NUM_KEYS) {
            keys[keyEvent.getKeyCode()] = true;
        }
    }
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() < NUM_KEYS) {
            keys[keyEvent.getKeyCode()] = false;
        }
    }

    public void focusLost(FocusEvent e) {
//        for(int i = 0; i < NUM_KEYS; i++)
//            keys[i] = false;
    }

    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }
    public int getScroll() {
        return scroll*-1;
    }

    public boolean isKey(int keycode) {
        return keys[keycode];
    }
    public boolean isKeyUp(int keycode) {
        return !keys[keycode] && keysLast[keycode];
    }
    public boolean isKeyDown(int keycode) {
        return keys[keycode] && !keysLast[keycode];
    }

    public boolean isButton(int keyCode) {
        return buttons[keyCode];
    }
    public boolean isButtonUp(int keyCode) {
        return !buttons[keyCode] && buttonsLast[keyCode];
    }
    public boolean isButtonDown(int keyCode) {
        return buttons[keyCode] && !buttonsLast[keyCode];
    }

    public void keyTyped(KeyEvent e) {

    }
    public void mouseClicked(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }
    public void focusGained(FocusEvent e) {

    }
}