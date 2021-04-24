package main.java.com.game.graphics;

import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private int numFrames;

    private int count;
    private int delay;

    public Animation() {
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        count = 0;
        delay = 2;
        numFrames = frames.length;
    }

    public void setDelay(int i) {
        delay = i;
    }

    public void update() {
        if(delay == -1)
            return;

        count++;

        if(count == delay) {
            currentFrame++;
            count =0;
        }
        if(currentFrame == numFrames) {
            currentFrame = 0;
        }
    }

    public int getDelay() { return delay; }

    public BufferedImage getImage() { return frames[currentFrame];}
}