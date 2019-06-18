package com.virajdesai.engine.gfx;

public class ImageTile extends Image{

    private int tileW, tileH;

    public ImageTile(String path, int tileW, int tileH) {
        super(path);
        this.tileW = tileW;
        this.tileH = tileH;
    }

    public int getTileW() {
        return tileW;
    }

    public int getTileH() {
        return tileH;
    }
}
