package main.java.com.game.states;
import main.java.com.game.GamePanel;
import main.java.com.game.graphics.Font;
import main.java.com.game.graphics.Sprite;
import main.java.com.game.util.KeyHandler;
import main.java.com.game.util.MouseHandler;
import main.java.com.game.util.Vector2f;

import java.awt.Graphics2D;

public class GameStateManager {

    private final GameState[] states;

    public static Vector2f map;
    public static int Now_Stage = 0;
    public static final int PLAY = 3;
    public static final int MENU = 1;
    public static final int GAME_OVER = 5;
    public static final int PAUSE = 4;
    public static final int SELECT = 2;
    public static final int INTRO = 0;


    public static int[][] player_map;
    public static int[][] enemy_map;

    public static Font font;


    public GameStateManager() throws Exception {

        map = new Vector2f(GamePanel.width,GamePanel.height);

        font = new Font("font/font.png",10,10);
        Sprite.currentFont = font;
        states = new GameState[6];
        states[INTRO] = new IntroState(this);
    }

    public boolean getState(int state) {
        return states[state] !=null;
    }

    public void pop(int state) {
        states[state] = null;
    }

    public void add(int state) throws Exception {
        if(states[state] != null)
            return;
        if(state == PLAY) {
           states[PLAY] = new PlayState(this);
        }

        if(state == MENU) {
            states[MENU] = new MenuState(this);
        }

        if(state == INTRO) {
            states[INTRO] = new IntroState(this);
        }

        if(state == SELECT) {
            states[SELECT] = new SelectState(this,Now_Stage);
        }

        if(state == PAUSE) {
            states[PAUSE] = new PauseState(this);
        }


    }

    public void add(int state,boolean isBadEnding) throws Exception {
        states[state] = new GameOverState(this,isBadEnding);
    }

    public void update() throws CloneNotSupportedException {
        for (GameState state : states) {
            if (state != null) {
                state.update();
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) throws Exception {
        for (GameState state : states) {
            if (state != null) {
                state.input(mouse, key);
            }
        }
    }

    public void render(Graphics2D g) {
        for (GameState state : states) {
            if (state != null) {
                state.render(g);
            }
        }
    }
}