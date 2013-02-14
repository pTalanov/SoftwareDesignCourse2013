package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import static ru.spbau.talanov.sd.drunkard.Direction.randomDirection;

/**
 * @author Pavel Talanov
 */
public final class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @NotNull
    public Position randomAdjacentPosition() {
        return positionInDirection(randomDirection());
    }

    @NotNull
    private Position positionInDirection(@NotNull Direction direction) {
        return new Position(x + direction.getDeltaX(), y + direction.getDeltaY());
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
