package main.java.com.game.states;

import main.java.com.game.util.KeyHandler;
import main.java.com.game.util.MouseHandler;

import java.awt.Graphics2D;

public abstract class GameState {

    public GameStateManager gsm;
    
    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void update() throws CloneNotSupportedException;
    public abstract void input(MouseHandler mouse, KeyHandler key) throws Exception;
    public abstract void render(Graphics2D g);
}