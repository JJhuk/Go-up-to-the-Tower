package main.java.com.game.entity;

import main.java.com.game.graphics.Sprite;
import main.java.com.game.util.Vector2f;

import java.awt.*;

public class Enemy extends Entity {

    public Enemy(Sprite sprite, Vector2f origin, int size) {
        super(sprite, origin, size);
        super.acc = 1f;
        super.maxSpeed = 3f;
        super.setAnimation(Direction.RIGHT, sprite.getSpriteArray(Direction.RIGHT),10);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.setColor(Color.blue);
        g.drawImage(ani.getImage(),(int)(pos.getWorldVar().x),(int)(pos.getWorldVar().y),size,size,null);
    }
}
