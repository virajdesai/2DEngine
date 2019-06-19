package com.virajdesai.engine.gfx;

public class LightRequest {

    public Light light;
    public int locX, locY;

    public LightRequest(Light light, int locX, int locY) {
        this.light = light;
        this.locX = locX;
        this.locY = locY;
    }
}
