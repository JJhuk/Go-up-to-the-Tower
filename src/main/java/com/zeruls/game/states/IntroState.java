package main.java.com.zeruls.game.states;

import main.java.com.zeruls.game.graphics.Sprite;
import main.java.com.zeruls.game.util.KeyHandler;
import main.java.com.zeruls.game.util.MouseHandler;
import main.java.com.zeruls.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IntroState extends GameState {
    private Sprite background[];
    private int introindex;
    public IntroState(GameStateManager gsm) {
        super(gsm);
        Init();
    }

    private void Init() {
        background = new Sprite[5];
        introindex = 0;
        for(int i=0; i<background.length;i++)  {
            background[i] = new Sprite("Intro"+i +".png");
        }
    }

    @Override
    public void update() throws CloneNotSupportedException {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) throws CloneNotSupportedException {
        if(mouse.getButton() == 1) {
            if(introindex < background.length)
                introindex++;
            else{
                gsm.pop(GameStateManager.INTRO);
                gsm.add(GameStateManager.SELECT);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawImg(g,background[introindex].getSprite(),new Vector2f(0,0),1024,768);
    }
}
