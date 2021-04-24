package main.java.com.game.states;

import java.awt.Graphics2D;

import main.java.com.game.graphics.Sprite;
import main.java.com.game.util.KeyHandler;
import main.java.com.game.util.MouseHandler;
import main.java.com.game.util.Vector2f;

public class PauseState extends GameState {
    private final Sprite BackGround;
    public PauseState(GameStateManager gsm) {
        super(gsm);
        BackGround = new Sprite("entity/pauseState.png");
    }

    @Override
    public void update() {
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawImg(g,BackGround.getSprite(),new Vector2f(0,0),1024,768);
    }

}
