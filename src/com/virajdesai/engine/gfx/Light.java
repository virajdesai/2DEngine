package com.virajdesai.engine.gfx;

public class Light {

    public static final int NONE = 0;
    public static final int FULL = 0;

    private int radius, diameter, color;
    private int[] lm;

    public Light(int radius, int color) {
        this.radius = radius;
        this.diameter = radius * 2;
        this.color = color;
        lm = new int[diameter * diameter];

        for(int y = 0; y < diameter; y++) {
            for(int x = 0; x < diameter; x++) {
                double distance = Math.sqrt((x - radius) * (x - radius) + (y - radius) * (y - radius));
                if(distance < radius) {
                    double power = 1 - (distance / radius);
                    lm[x + y * diameter] = ((int)(((color >> 16) & 0xff) * power) << 16 | (int)(((color >> 8) & 0xff) * power) << 8 | (int)((color & 0xff) * power));
                } else {
                    lm[x + y * diameter] = 0;
                }
            }
        }
    }

    public  int getLightValue(int x, int y) {
        if(x < 0 || x >= diameter || y < 0 || y >= diameter)
            return 0;
        return lm[x + y * diameter];

    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int[] getLm() {
        return lm;
    }

    public void setLm(int[] lm) {
        this.lm = lm;
    }
}
