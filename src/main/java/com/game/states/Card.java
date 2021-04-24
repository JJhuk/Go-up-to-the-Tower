package main.java.com.game.states;

import main.java.com.game.entity.Direction;
import main.java.com.game.graphics.Sprite;
import main.java.com.game.util.Vector2f;

import java.awt.*;

public class Card implements Cloneable{
    private Sprite front_sprite;
    private Sprite back_sprite;
    private Vector2f pos;
    private int DM;
    private int MP;
    private boolean isSelected;
    private boolean isEnemySelected;
    private boolean isBackSprite;
    private Direction direction;
    private boolean[][] arrange;
    public final static int ATTACK_CARD = 0;
    public final static int MOVE_CARD = 1;
    public final static int HEAL_CARD = 2;    //마나도 포함
    public final static int UTIL_CARD = 3;    //쉴드
    private final int attribute;
    private int plus_hp;
    private int plus_mp;

    private void init_card(Sprite front_sprite, Vector2f origin) {
        this.front_sprite = front_sprite;
        back_sprite = new Sprite("entity/card_Back.png");
        this.pos = origin;
        this.isBackSprite = false;
        isSelected = false;
    }
    //공격 카드
    public Card(Sprite front_sprite, Vector2f origin, int DM, int MP, boolean[][] arrange) {
        init_card(front_sprite, origin);
        this.DM = DM;
        this.MP = MP;
        this.arrange = arrange;
        attribute = ATTACK_CARD;
        this.isBackSprite = false;
    }

    //이동 카드
    public Card(Sprite front_sprite, Vector2f origin, Direction direction) {
        init_card(front_sprite, origin);
        this.direction = direction;
        attribute = MOVE_CARD;
        this.isBackSprite = false;
    }

    //회복 카드
    public Card(Sprite front_sprite, Vector2f origin, int plus, boolean isHeal) {
        init_card(front_sprite, origin);
        if(isHeal) {
            this.plus_hp = plus;
            this.plus_mp = 0;
        }

        else {
            this.plus_mp = plus;
            this.plus_hp = 0;
        }

        attribute = HEAL_CARD;
        this.isBackSprite = false;
    }

    //방어 카드
    public Card(Sprite front_sprite, Vector2f origin) {
        init_card(front_sprite,origin);
        this.isBackSprite = false;
        attribute = UTIL_CARD;
    }

    public int getDM() { return DM; }

    public Sprite getFront_sprite() { return front_sprite; }
    public Vector2f getPos() { return pos; }
    public Direction getDirection() {return direction;}
    public int getMP() { return MP; }
    public int getAttribute() {return attribute;}
    public int getPlus_hp() {return plus_hp;}
    public int getPlus_mp() {return plus_mp;}
    public boolean isEnemySelected() {return isEnemySelected;}
    public boolean isSelected() {
        return isSelected;
    }
    public boolean[][] getArrange() {return arrange; }
    public void SetIsBackSprite(boolean isUsed) {
        this.isBackSprite = isUsed;
    }
    public void setSelected(boolean selected) {this.isSelected = selected;}
    public void setPos(Vector2f pos) {this.pos = pos;}
    public void setEnemySelected(boolean selected) {this.isEnemySelected = selected;}

    public void render(Graphics2D g) {
        g.setColor(Color.pink);
        if(!isBackSprite) //카드 선택할때,사용할때 앞
            g.drawImage(front_sprite.getSprite(),(int)pos.x,(int)pos.y,null);
        else    //시작할때 뒤
            g.drawImage(back_sprite.getSprite(),(int)pos.x,(int)pos.y,null);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
