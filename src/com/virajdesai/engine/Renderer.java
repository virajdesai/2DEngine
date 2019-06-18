package com.virajdesai.engine;

import com.virajdesai.engine.gfx.Font;
import com.virajdesai.engine.gfx.Image;
import com.virajdesai.engine.gfx.ImageRequest;
import com.virajdesai.engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Renderer {

    private Font font = Font.STANDARD;
    private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();

    private int pW, pH;
    private int[] p;
    private int[] zb;

    private int zDepth = 1;
    private boolean processing = false;

    public Renderer(GameContainer gc) {

        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zb = new int[p.length];
    }

    public void clear() {
        for(int i = 0; i < p.length; i++) {
            p[i] = 0xff0000ff;
            zb[i] = 0;
        }

    }

    public void process() {

        processing = true;

        Collections.sort(imageRequest, new Comparator<ImageRequest>() {
            @Override
            public int compare(ImageRequest i0, ImageRequest i1) {
                if(i0.zDepth < i1.zDepth)
                    return -1;
                if(i0.zDepth > i1.zDepth)
                    return 1;
                return 0;
            }
        });

        for(int i = 0; i < imageRequest.size(); i++) {
            ImageRequest ir = imageRequest.get(i);
            setzDepth(ir.zDepth);
            drawImage(ir.image, ir.offX, ir.offY);
        }

        imageRequest.clear();
        processing = false;
    }

    public void setPixel(int x, int y, int value) {

        int alpha = ((value >> 24) & 0xff);

        if(x < 0 || x >= pW || y < 0 || y >= pH || alpha == 0)
            return;

        int index = x + y * pW;

        if (zb[index] > zDepth)
            return;

        zb[index] = zDepth;

        if(alpha == 255) {
            p[index] = value;
        } else {

            //Alpha blending
            int pixelColor = p[index];

            int newR = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
            int newG = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
            int newB = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value & 0xff)) * (alpha / 255f));

            p[index] = (255 << 24 | newR << 16 | newG << 8 | newB);
        }


    }

    public void drawText(String text, int offX, int offY, int color) {

        text = text.toUpperCase();
        int offset = 0;

        for(int i = 0; i < text.length(); i++) {

            int unicode = text.codePointAt(i) - 32;

            for(int y = 0; y < font.getFontImage().getH(); y++) {
                for(int x = 0; x < font.getWidths()[unicode]; x++) {
                    if(font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getW()] == 0xffffffff) {
                        setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }
            offset += font.getWidths()[unicode];
        }
    }

    public void drawImage(Image image, int offX, int offY) {

        if(image.isAlpha() && !processing) {
            imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
            return;
        }

        //Keep renders in bounds of frame
        if(offX < -image.getW())
            return;
        if(offY < -image.getH())
            return;
        if(offX >= pW)
            return;
        if(offY >= pH)
            return;

        int newX = 0, newY = 0;
        int newWidth = image.getW();
        int newHeight = image.getH();



        //Clipping
        if(offX < 0)
            newX -= offX;
        if(offY < 0)
            newY -= offY;
        if(newWidth + offX >= pW)
            newWidth -= newWidth + offX - pW;
        if(newHeight + offY >= pH)
            newHeight -= newHeight + offY - pH;


        for(int y = newY; y < newHeight; y++)
            for(int x = newX; x < newWidth; x++)
                setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);


    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {

        if(image.isAlpha() && !processing) {
            imageRequest.add(new ImageRequest(image.getTileImage(tileX, tileY), zDepth, offX, offY));
            return;
        }

        //Keep renders in bounds of frame
        if(offX < -image.getTileW())
            return;
        if(offY < -image.getTileH())
            return;
        if(offX >= pW)
            return;
        if(offY >= pH)
            return;

        int newX = 0, newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();



        //Clipping
        if(offX < 0)
            newX -= offX;
        if(offY < 0)
            newY -= offY;
        if(newWidth + offX >= pW)
            newWidth -= newWidth + offX - pW;
        if(newHeight + offY >= pH)
            newHeight -= newHeight + offY - pH;


        for(int y = newY; y < newHeight; y++)
            for(int x = newX; x < newWidth; x++)
                setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
    }

    public void drawRect(int offX, int offY, int width, int height, int color) {
        for(int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }
        for(int x = 0; x <= width; x++) {
            setPixel(x + offX,  offY, color);
            setPixel(x + offX, offY + height, color);
        }
    }

    public void drawFillRect(int offX, int offY, int width, int height, int color) {

        //Keep renders in bounds of frame
        if(offX < -width)
            return;
        if(offY < -height)
            return;
        if(offX >= pW)
            return;
        if(offY >= pH)
            return;

        int newX = 0, newY = 0;
        int newWidth = width;
        int newHeight = height;



        //Clipping
        if(offX < 0)
            newX -= offX;
        if(offY < 0)
            newY -= offY;
        if(newWidth + offX >= pW)
            newWidth -= newWidth + offX - pW;
        if(newHeight + offY >= pH)
            newHeight -= newHeight + offY - pH;

        for(int y = newY; y < newHeight; y++)
            for(int x = newX; x < newWidth; x++)
                setPixel(x + offX, y + offY, color);
    }

    public int getzDepth() {
        return zDepth;
    }

    public void setzDepth(int zDepth) {
        this.zDepth = zDepth;
    }
}
