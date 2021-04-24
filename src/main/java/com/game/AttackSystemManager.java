package main.java.com.game;
import main.java.com.game.entity.Direction;
import main.java.com.game.util.Vector2f;
import main.java.com.game.states.GameStateManager;
import main.java.com.game.states.PlayState;

public class AttackSystemManager  {

    public static Vector2f enemy_pos;
    public static Vector2f player_pos;
    public static int Player_HP;
    public static int Player_MP;
    public static int Enemy_HP;
    public static int Enemy_MP;
    private final int CHAR = 1;
    private final int EMPTY = 0;
    public static int dx = 150;
    public static int dy = 75;

    public void ResetEnemyMap() {
        for(int i=0;i<3;i++) {
            for(int j=0;j<4;j++) {
                GameStateManager.enemy_map[i][j] = EMPTY;
            }
        }
    }

    public AttackSystemManager() {

        if(GameStateManager.player_map==null && GameStateManager.enemy_map ==null) {
            GameStateManager.player_map = new int[3][4];
            GameStateManager.enemy_map = new int[3][4];
            ResetEnemyMap();
            ResetEnemyMap();
            GameStateManager.player_map[1][0] = CHAR;
            GameStateManager.enemy_map[1][3] = CHAR;
        }
    }

    public void Player_swap(Vector2f begin, Vector2f end) {
        swap_array(begin, end, GameStateManager.player_map);
    }

    public void Enemy_swap(Vector2f begin, Vector2f end) {
        swap_array(begin, end, GameStateManager.enemy_map);
    }

    private void swap_array(Vector2f begin, Vector2f end, int[][] arr) {
        try{
            int temp = arr[(int)begin.x][(int)begin.y];  //moving
            arr[(int)begin.x][(int)begin.y] =  arr[(int)end.x][(int)end.y];
            arr[(int)end.x][(int)end.y] = temp;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public Vector2f getPlayerIndex() {
        for(int i=0;i<3;i++) {
            for(int j=0;j<4;j++) {
                if(GameStateManager.player_map[i][j] == CHAR) {
                    return new Vector2f(i,j);
                }
            }
        }
        System.out.println("Cannot Find Player");
        return null;
    }

    public Vector2f getEnemyIndex() {
        for(int i=0;i<3;i++) {
            for(int j=0;j<4;j++) {
                if(GameStateManager.enemy_map[i][j] == CHAR) {
                    return new Vector2f(i,j);
                }
            }
        }
        System.out.println("Cannot Find enemy");
        return null;
    }

    public void MovePlayer(Direction direction) {
        switch(direction) {
            case UP :
                Player_swap(getPlayerIndex(), new Vector2f(getPlayerIndex().x - 1, getPlayerIndex().y));
                return;
            case DOWN:
                Player_swap(getPlayerIndex(), new Vector2f(getPlayerIndex().x + 1, getPlayerIndex().y));
                return;
            case LEFT:
                Player_swap(getPlayerIndex(), new Vector2f(getPlayerIndex().x, getPlayerIndex().y - 1));
                return;
            case RIGHT:
                Player_swap(getPlayerIndex(), new Vector2f(getPlayerIndex().x, getPlayerIndex().y + 1));
                return;
            default:
        }
    }

    public void MoveEnemy(Direction direction) {
        switch(direction) {
            case UP :
                Enemy_swap(getEnemyIndex(), new Vector2f(getEnemyIndex().x - 1, getEnemyIndex().y));
                return;
            case DOWN:
                Enemy_swap(getEnemyIndex(), new Vector2f(getEnemyIndex().x + 1, getEnemyIndex().y));
                return;
            case LEFT:
                Enemy_swap(getEnemyIndex(), new Vector2f(getEnemyIndex().x, getEnemyIndex().y - 1));
                return;
            case RIGHT:
                Enemy_swap(getEnemyIndex(), new Vector2f(getEnemyIndex().x, getEnemyIndex().y + 1));
                return;
            default:
        }
    }

    public boolean PlayerAttack(boolean[][] arr, boolean isReverse) {
        Vector2f np = getPlayerIndex();
        if(isReverse)
            return CheckAttack(np, GameStateManager.enemy_map,ReverseArr(arr));
        else
            return CheckAttack(np, GameStateManager.enemy_map,arr);
    }

    public boolean EnemyAttack(boolean[][] arr, boolean isReverse) {
        Vector2f np = getEnemyIndex();
        if(isReverse)
            return CheckAttack(np, GameStateManager.player_map,ReverseArr(arr));
        else
            return CheckAttack(np,GameStateManager.player_map,arr);
    }

    public boolean[][] ReverseArr(boolean[][] arr) {

        boolean[][] result = new boolean[3][3];

        result[0][0] = arr[0][1];
        result[1][0] = arr[1][2];
        result[2][0] = arr[2][2];
        result[0][2] = arr[0][0];
        result[1][2] = arr[1][0];
        result[2][2] = arr[2][0];

        return result;
    }

    private boolean CheckAttack(Vector2f np, int[][] map , boolean[][] arr) {
        System.out.println("Player x: " + getPlayerIndex().x + " y: " +getPlayerIndex().y);
        System.out.println("Enemy x: "+getEnemyIndex().x + "y: "+getEnemyIndex().y);
        PlayState.DrawAttack = new boolean[3][4];
        boolean isHeat = false;
        for(int i = (int)np.x-1,i1 = 0; i<=(int)np.x+1;i++,i1++) {
            for(int j = (int)np.y -1,j1 = 0; j<=(int)np.y+1;j++,j1++) {
                System.out.println("SKILL Check x: " + i1 + " y: " +j1);
                System.out.println("Arrange Check x: "+i + "y: "+j);
                if(arr[i1][j1]) {   //공격 범위가 유효할때
                    if(i>=0 && i<3 && j>=0  && j<4) {
                        PlayState.DrawAttack[i][j] = true;
                        if(map[i][j] != EMPTY) {
                            isHeat = true;
                            System.out.println(i + ", " +j + "에서 상대방이 맞음");
                        }
                    }
                }
            }
        }
        return isHeat;
    }
}
