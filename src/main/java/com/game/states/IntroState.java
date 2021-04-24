package main.java.com.game.states;

import main.java.com.game.graphics.Sprite;
import main.java.com.game.util.KeyHandler;
import main.java.com.game.util.MouseHandler;
import main.java.com.game.util.MusicPlayer;
import main.java.com.game.util.Vector2f;

import java.awt.*;

public class IntroState extends GameState {
    private Sprite[] background;
    private int introIndex;
    private int count;
    private MusicPlayer player;
    public IntroState(GameStateManager gsm) throws Exception {
        super(gsm);
        Init();
    }

    private void Init() throws Exception {
        background = new Sprite[5];
        introIndex = 0;
        for(int i=0; i<background.length;i++)  {
            background[i] = new Sprite("Background/Intro"+i +".png");
        }
        player = new MusicPlayer("audio/Intro.mp3",true);
        player.start();
        count = 0;
    }

    @Override
    public void update() throws CloneNotSupportedException {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) throws Exception {
        if(mouse.getButton() == 1) {
            if(introIndex < background.length) {
                if(count != 4)
                    count++;
                else {
                    count = 0;
                    introIndex++;
                }
            }
            else{
                player.close();
                gsm.pop(GameStateManager.INTRO);
                gsm.add(GameStateManager.SELECT);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(introIndex < background.length)
            Sprite.drawImg(g,background[introIndex].getSprite(),new Vector2f(0,0),1024,768);
    }
}
