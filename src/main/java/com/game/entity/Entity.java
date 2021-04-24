package main.java.com.game.entity;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import main.java.com.game.AttackSystemManager;
import main.java.com.game.graphics.Animation;
import main.java.com.game.graphics.Sprite;
import main.java.com.game.util.Vector2f;

public abstract class Entity {

    protected Direction currentAnimation;
    protected Direction direction;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected Vector2f pre_pos;

    protected boolean isReverse;   //지금 오른쪽 왼쪽을 바라보고 있느냐

    protected float dx;
    protected float dy;

    protected float maxSpeed = 4f;
    protected float acc = 3f;

    protected int size;
    protected int HP;
    protected int MP;


    public Entity(Sprite sprite, Vector2f origin, int size) {
        this.sprite = sprite;
        this.size = size;
        pos = origin;
        pre_pos = origin.clone();
        HP = 100;
        MP = 100;
        ani = new Animation();
        direction = Direction.STOP;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setHP(int HP) {this.HP = HP;}
    public void setIsReverse(boolean isReverse) {this.isReverse = isReverse;}
    public void setMP(int MP) {this.MP = MP;}
    public void setPos(Vector2f pos) {this.pos = pos;}

    public Vector2f getPos() {return pos;}
    public String getHP() {return Integer.toString(HP);}
    public String getMP() {return Integer.toString(MP);}
    public boolean getIsReverse() {return this.isReverse;}

    public void setAnimation(Direction direction, BufferedImage[] frames, int delay) {
        currentAnimation = direction;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void animate() {
        if(direction == Direction.RIGHT) {
            if(currentAnimation != Direction.LEFT || ani.getDelay() == -1) {
                setAnimation(Direction.LEFT, sprite.getSpriteArray(Direction.LEFT), 5);
            }
        }
        else if(direction == Direction.LEFT) {
            if(currentAnimation != Direction.RIGHT || ani.getDelay() == -1) {
                setAnimation(Direction.RIGHT, sprite.getSpriteArray(Direction.RIGHT), 5);
            }
        }
        else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }


    public void update() {
        animate();
        ani.update();
        move();
        pos.x += dx;
        pos.y += dy;
        go();
    }

    public abstract void render(Graphics2D g) ;

    public void go() {
        switch (direction) {
            case DOWN:
                if (pos.y - pre_pos.y >= AttackSystemManager.dy)
                    direction = Direction.STOP;
                break;
            case UP:
                if (pre_pos.y - pos.y >= AttackSystemManager.dy)
                    direction = Direction.STOP;
                break;
            case RIGHT:
                if (pos.x - pre_pos.x >= AttackSystemManager.dx)
                    direction = Direction.STOP;
                break;
            case LEFT:
                if (pre_pos.x - pos.x >= AttackSystemManager.dx)
                    direction = Direction.STOP;
                break;
            case STOP:
                pre_pos.x = pos.x;
                pre_pos.y = pos.y;
                break;
        }
    }

    public void move() {
        switch (direction)
        {
            case STOP:
                dx = 0;
                dy = 0;
                break;
            case UP:
                dy -= acc;
                if(dy < -maxSpeed) {
                    dy = -maxSpeed;
                }
                break;
            case DOWN:
                dy += acc;
                if(dy > maxSpeed) {
                    dy = maxSpeed;
                }
                break;
            case LEFT:
                dx -= acc;
                if(dx < -maxSpeed) {
                    dx = -maxSpeed;
                }
                break;
            case RIGHT:
                dx += acc;
                if(dx > maxSpeed) {
                    dx = maxSpeed;
                }
                break;
        }
    }

    public void setDir(Direction dir) {
        direction = dir;
    }
}