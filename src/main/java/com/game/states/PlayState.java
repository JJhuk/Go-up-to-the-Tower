package main.java.com.game.states;

import main.java.com.game.AttackSystemManager;
import main.java.com.game.entity.Direction;
import main.java.com.game.graphics.Sprite;
import main.java.com.game.util.*;
import main.java.com.game.entity.Enemy;
import main.java.com.game.entity.Player;

import java.awt.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class PlayState extends GameState {
    private final Player player;
    private final Sprite BackGround;
    private Enemy Magiction;
    private Enemy Devil;
    private Enemy DeathKnight;
    private Enemy Skeleton;
    private Card[] player_cards;
    private Card[] enemy_cards;
    private Vector2f[] enemy_card_pos;
    private final Vector2f[] pos_Selected_Cards;
    public static boolean[][] DrawAttack;

    public static Vector2f player_pre_pos;
    public static Vector2f enemy_pre_pos;

    private AttackSystemManager ASM;
    public static Vector2f map;

    private int CurrentIndex;
    private boolean TimerStart;


    private MusicPlayer Attacked_Sound;

    public PlayState(GameStateManager gsm) throws Exception {
        super(gsm);
        TimerStart = false;
        pos_Selected_Cards = new Vector2f[3];
        pos_Selected_Cards[0] = new Vector2f(300,600);
        pos_Selected_Cards[1] = new Vector2f(165,600);
        pos_Selected_Cards[2] = new Vector2f(30,600);
        CurrentIndex = 0;
        map = new Vector2f();
        Attacked_Sound = new MusicPlayer("audio/SoundEffect/Attacked.mp3",false);
        if(AttackSystemManager.enemy_pos == null)
            player = new Player(new Sprite("entity/PlayerFormatted.png"), new Vector2f(250, 350), 64); //map's vector is 0
        else {
            player = new Player(new Sprite("entity/PlayerFormatted.png"), AttackSystemManager.player_pos,64);
            player.setHP(AttackSystemManager.Player_HP);
            player.setMP(AttackSystemManager.Player_MP);
        }
        makeEnemy();
        BackGround = new Sprite("entity/PlayBackground.png");
        ASM = new AttackSystemManager();
        setCards();
        System.out.println(GameStateManager.Now_Stage + "스테이지 입니다.");
    }


    private Enemy makeEnemy(String kind,float x,float y) {
        return new Enemy(new Sprite("entity/"+kind+"Formatted.png"), new Vector2f(x,y),64);
    }

    private void makeEnemy() {
        enemy_card_pos = new Vector2f[3];
        enemy_card_pos[0] = new Vector2f(600,600);
        enemy_card_pos[1] = new Vector2f(735,600);
        enemy_card_pos[2] = new Vector2f(870,600);
        Vector2f pos = new Vector2f(700,350);

        switch(GameStateManager.Now_Stage) {
            case SelectState.SKELETON:
                Skeleton = makeEnemy("Skeleton",pos.x,pos.y);
                break;
            case SelectState.DEATHKNIGHT:
                DeathKnight = makeEnemy("DeathKnight",pos.x,pos.y);
                break;
            case SelectState.MAGICTION:
                Magiction = makeEnemy("Magiction",pos.x,pos.y);
                break;
            case SelectState.DEVIL:
                Devil = makeEnemy("Devil",pos.x,pos.y);
                break;
        }
        if(AttackSystemManager.enemy_pos != null) {
            assert getNowStage() != null;
            getNowStage().setHP(AttackSystemManager.Enemy_HP);
            getNowStage().setMP(AttackSystemManager.Enemy_MP);
            getNowStage().setPos(AttackSystemManager.enemy_pos);
        }
    }

    private Enemy getNowStage() {
        switch (GameStateManager.Now_Stage) {
            case SelectState.SKELETON:
                return Skeleton;
            case SelectState.DEATHKNIGHT:
                return DeathKnight;
            case SelectState.MAGICTION:
                return Magiction;
            case SelectState.DEVIL:
                return Devil;
        }
        return null;
    }

    private void setCards() {
        player_cards = new Card[3];
        enemy_cards = new Card[3];
        try {
            if (SelectState.player_cards.peek() != null) {
                for (int i = 0; i < 3; i++) {
                    player_cards[i] = SelectState.player_cards.peek();
                    assert player_cards[i] != null;
                    player_cards[i].SetIsBackSprite(true);
                    player_cards[i].setPos(pos_Selected_Cards[i]);
                    System.out.println(Objects.requireNonNull(SelectState.player_cards.poll()).getFront_sprite().getFileName() + "카드를 골랐내요 !");

                    enemy_cards[i] = SelectState.enemy_cards.peek();
                    assert enemy_cards[i] != null;
                    enemy_cards[i].SetIsBackSprite(true);
                    enemy_cards[i].setPos(enemy_card_pos[i]);
                    System.out.println(Objects.requireNonNull(SelectState.enemy_cards.poll()).getFront_sprite().getFileName() + "적이 카드를 골랐네요 !");

                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "다 안고르고 오셨군요!");
        }
    }

    private void drawString(Graphics2D g) {
        drawHPMP(g);
        drawSTAGE(g);
    }

    private void drawHPMP(Graphics2D g) {
        Sprite.drawArray(g, "HP : " + player.getHP(), new Vector2f(80, 30), 32, 24);
        Sprite.drawArray(g, "MP : " + player.getMP(), new Vector2f(80, 60), 32, 24);

        assert getNowStage() != null;
        Sprite.drawArray(g, "HP : " + getNowStage().getHP(), new Vector2f(800, 30), 32, 24);
        Sprite.drawArray(g, "MP : " + getNowStage().getMP(), new Vector2f(800, 60), 32, 24);

    }

    private void drawSTAGE(Graphics2D g) {
        Sprite.drawArray(g,"STAGE : " + GameStateManager.Now_Stage, new Vector2f(420,40),32,24);

    }

    private void drawEnemy(Graphics2D g) {
        assert getNowStage() != null;
        getNowStage().render(g);
    }

    public void update() throws CloneNotSupportedException {
        player.update();
        assert getNowStage() != null;
        getNowStage().update();
    }

    public void open_playerCard() throws Exception {
        boolean isHit;
        switch (player_cards[CurrentIndex].getAttribute()) {
            case Card.ATTACK_CARD :
               if(Integer.parseInt(player.getMP())>player_cards[CurrentIndex].getMP()) {
                   Attacked_Sound.start();
                   player.setMP((Integer.parseInt(player.getMP()) - player_cards[CurrentIndex].getMP()));
                   isHit =ASM.PlayerAttack(player_cards[CurrentIndex].getArrange(),player.getIsReverse());
                   if(isHit) {
                       System.out.println("공격 성공!");
                       assert getNowStage() != null;
                       getNowStage().setHP((Integer.parseInt(getNowStage().getHP()) - player_cards[CurrentIndex].getDM()));

                   }
                   Attacked_Sound = new MusicPlayer("audio/SoundEffect/Attacked.mp3",false);
               }
               else {
                   System.out.println("마나가 부족합니다!");
               }
                break;
            case Card.HEAL_CARD:
                player.setHP(Integer.parseInt(player.getHP()) + player_cards[CurrentIndex].getPlus_hp());
                player.setMP(Integer.parseInt(player.getMP()) + player_cards[CurrentIndex].getPlus_mp());
                if(Integer.parseInt(player.getHP()) > 100)
                    player.setHP(100);
                else if(Integer.parseInt(player.getMP()) >100)
                    player.setMP(100);
                break;
            case Card.MOVE_CARD:
                player_pre_pos = player.getPos().clone();
                Direction direction = player_cards[CurrentIndex].getDirection();
                ASM.MovePlayer(direction);
                player.setDir(direction);
                if(direction == Direction.LEFT)
                    player.setIsReverse(true);
                else if(direction == Direction.RIGHT)
                    player.setIsReverse(false);
            case Card.UTIL_CARD:
                break;
        }
    }

    public void enemy_heal(Enemy enemy) {
        enemy.setHP(Integer.parseInt(enemy.getHP()) + enemy_cards[CurrentIndex].getPlus_hp());
        enemy.setMP(Integer.parseInt(enemy.getMP()) + enemy_cards[CurrentIndex].getPlus_mp());
        if(Integer.parseInt(enemy.getHP()) > 100)
            enemy.setHP(100);
        else if(Integer.parseInt(enemy.getMP()) >100)
            enemy.setMP(100);
    }

    public void enemy_move(Enemy enemy) {
        enemy_pre_pos = enemy.getPos().clone();
        Direction direction = enemy_cards[CurrentIndex].getDirection();
        ASM.MoveEnemy(direction);

        assert getNowStage() != null;
        getNowStage().setDir(direction);
        if(direction == Direction.LEFT) {
            getNowStage().setIsReverse(false);
        }
        else if (direction == Direction.RIGHT) {
            getNowStage().setIsReverse(true);
        }
    }


    public void open_enemyCard() {
        boolean isHit;
        switch (enemy_cards[CurrentIndex].getAttribute()) {
            case Card.ATTACK_CARD :
                assert getNowStage() != null;
                if(Integer.parseInt(getNowStage().getMP())>enemy_cards[CurrentIndex].getMP()) {
                    getNowStage().setMP((Integer.parseInt(getNowStage().getMP())- enemy_cards[CurrentIndex].getMP()));
                    isHit = ASM.EnemyAttack(enemy_cards[CurrentIndex].getArrange(),getNowStage().getIsReverse());
                    if(isHit) {
                        System.out.println("상대방이 공격에 성공했습니다!");
                        player.setHP((Integer.parseInt(player.getHP()) - enemy_cards[CurrentIndex].getDM()));
                    }
                }
                else {
                    System.out.println("마나가 부족합니다!");
                }
                break;
            case Card.HEAL_CARD:
                assert getNowStage() != null;
                enemy_heal(getNowStage());
                break;
            case Card.MOVE_CARD:
                assert getNowStage() != null;
                enemy_move(getNowStage());
                break;
            case Card.UTIL_CARD:
                break;
        }
    }

    public void openCard() throws Exception {
        open_playerCard();
        open_enemyCard();
        CurrentIndex++;
    }
    public void input(MouseHandler mouse, KeyHandler key) throws Exception {
        key.escape.tick();
        player.input(mouse);
        if (key.escape.clicked) {
            System.out.println("Pressed by PlayState");
            if (gsm.getState(GameStateManager.PAUSE)) {
                gsm.pop(GameStateManager.PAUSE);
            } else {
                gsm.add(GameStateManager.PAUSE);
            }
        }
        if (key.enter.clicked) {
            System.out.println("Pre");
        }
        if (mouse.getButton() == 1) {
            if(Integer.parseInt(player.getHP()) <= 0 || Integer.parseInt(Objects.requireNonNull(getNowStage()).getHP()) <= 0) {
                if(Integer.parseInt(player.getHP()) <= 0) {
                    //player lose
                    gsm.pop(GameStateManager.PLAY);
                    gsm.add(GameStateManager.GAME_OVER,true);
                }
                if( GameStateManager.Now_Stage++ >= 3) {
                    AttackSystemManager.enemy_pos = null;
                    AttackSystemManager.player_pos = null;
                    ASM = null;
                    gsm.pop(GameStateManager.PLAY);
                    gsm.add(GameStateManager.GAME_OVER,false);
                }
                else {
                    GameStateManager.player_map = null;
                    GameStateManager.enemy_map = null;
                    AttackSystemManager.enemy_pos = null;
                    AttackSystemManager.player_pos = null;
                    gsm.pop(GameStateManager.PLAY);
                    gsm.add(GameStateManager.SELECT);
                }
                //Enemy lose
            }
            else {
                System.out.println("x : " + mouse.getX() + " " + "y : " + mouse.getY());
                if(CurrentIndex !=3) {
                    player_cards[CurrentIndex].SetIsBackSprite(false);
                    enemy_cards[CurrentIndex].SetIsBackSprite(false);
                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("Timer Start!");
                            try {
                                openCard();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            timer.cancel();
                            TimerStart = false;
                        }
                    };
                    if(!TimerStart) {
                        timer.schedule(timerTask,3000);
                        TimerStart = true;
                    }
                }
                else {  //끝
                    AttackSystemManager.player_pos = player.getPos().clone();
                    assert getNowStage() != null;
                    AttackSystemManager.enemy_pos = getNowStage().getPos().clone();
                    AttackSystemManager.Enemy_HP = Integer.parseInt(getNowStage().getHP());
                    AttackSystemManager.Enemy_MP = Integer.parseInt(getNowStage().getMP());
                    AttackSystemManager.Player_HP = Integer.parseInt(player.getHP());
                    AttackSystemManager.Player_MP = Integer.parseInt(player.getMP());
                    gsm.pop(GameStateManager.PLAY);
                    gsm.add(GameStateManager.SELECT);
                }
            }
        }
    }

    public void render(Graphics2D g) {
        Sprite.drawImg(g, BackGround.getSprite(), new Vector2f(0, 0), 1024, 768);
        drawString(g);
        if(Integer.parseInt(player.getHP()) > 0)
            player.render(g);
        assert getNowStage() != null;
        if(Integer.parseInt(getNowStage().getHP())>0)
            drawEnemy(g);
        for(int i=0;i<3;i++) {
            player_cards[i].render(g);
            enemy_cards[i].render(g);
        }
    }
}