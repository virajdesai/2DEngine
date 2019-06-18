package com.virajdesai.engine;

public class GameContainer implements Runnable{

    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;

    private boolean isRunning = false;
    private final int UPS = 60;
    private int width = 320, height = 240;
    private float scale = 3f;
    private String title = "Engine v2.0";

    public GameContainer(AbstractGame game) {
        this.game = game;
    }

    public void start() {

        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input (this);
        thread = new Thread(this);
        thread.run();
    }

    public void stop() {

    }

    //Game loop
    public void run() {

        isRunning = true;

        long initialTime = System.nanoTime();
        final double time = 1000000000 / UPS;
        double delta = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();


        while (isRunning) {

            long currentTime = System.nanoTime();
            delta += (currentTime - initialTime) / time;
            initialTime = currentTime;

            if (delta >= 1) {
                game.update(this, (float)1.0/UPS);
                input.update();
                ticks++;
                delta--;
            }

            renderer.clear();
            game.render(this, renderer);
            renderer.process();
            window.update();
            frames++;

            try {
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(System.currentTimeMillis() - timer > 1000) {
                System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
                frames = 0;
                ticks = 0;
                timer += 1000;
            }


        }

        dispose();

    }

    public Input getInput() {
        return input;
    }

    private void dispose() {

    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public float getScale() {
        return scale;
    }
    public String getTitle() {
        return title;
    }
    public Window getWindow() {
        return window;
    }
}
