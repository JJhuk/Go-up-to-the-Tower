package main.java.com.game.entity;

import java.awt.*;


import main.java.com.game.graphics.Sprite;
import main.java.com.game.util.MouseHandler;
import main.java.com.game.util.Vector2f;

public class Player extends Entity {

    public Player(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        super.setAnimation(Direction.LEFT,sprite.getSpriteArray(Direction.LEFT),10);
        super.acc = 2f;   //2f
        super.maxSpeed = 3f;
    }

    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.blue);
        g.drawImage(ani.getImage(),(int)(pos.getWorldVar().x),(int)(pos.getWorldVar().y),size,size,null);
    }

    public void input(MouseHandler mouse) {
        if(mouse.getButton() == 1) {
            System.out.println("Player: " + pos.x + ", " + pos.y);
        }
    }
}