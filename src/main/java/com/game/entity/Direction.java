package main.java.com.game.entity;

public enum Direction {
    STOP(4),
    UP(3),
    DOWN(2),
    LEFT(1),
    RIGHT(0);

    private final int value;

    Direction(int value)
    {
        this.value = value;
    }

    public int GetValue()
    {
        return value;
    }
}
