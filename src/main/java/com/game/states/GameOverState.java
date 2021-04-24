package main.java.com.game.states;

import main.java.com.game.graphics.Sprite;
import main.java.com.game.util.KeyHandler;
import main.java.com.game.util.MouseHandler;
import main.java.com.game.util.MusicPlayer;
import main.java.com.game.util.Vector2f;

import java.awt.Graphics2D;


public class GameOverState extends GameState {
    private final String word;

    public GameOverState(GameStateManager gsm,boolean isBadEnding) throws Exception {
        super(gsm);
        MusicPlayer musicPlayer;
        if(isBadEnding) {
            musicPlayer = new MusicPlayer("audio/BadEnding.mp3",true);
            word = "You lose...";
        }
        else {
            musicPlayer = new MusicPlayer("audio/Ending.mp3",true);
            word = "You save princess!!";
        }
        musicPlayer.start();
    }

    @Override
    public void update() {
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }

    @Override
    public void render(Graphics2D g) {
        // TODO Auto-generated method stub
        Sprite.drawArray(g,word,new Vector2f(1024 >> 1, 768 >> 1),32,24);
    }

}
