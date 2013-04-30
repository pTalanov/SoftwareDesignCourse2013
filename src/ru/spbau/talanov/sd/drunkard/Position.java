package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static ru.spbau.talanov.sd.drunkard.Direction.randomDirection;

/**
 * @author Pavel Talanov
 */
public final class Position {
    @NotNull
    public static List<Position> allPositionsInRadius(@NotNull Position center, int radius) {
        assert radius >= 0;
        List<Position> result = new ArrayList<>();
        for (int dx = -radius; dx <= radius; ++dx) {
            for (int dy = -radius; dy <= radius; ++dy) {
                if (Math.abs(dx) + Math.abs(dy) <= radius) {
                    result.add(new Position(center.x + dx, center.y + dy));
                }
            }
        }
        return result;
    }

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
    public List<Position> adjacentPositions() {
        ArrayList<Position> result = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            result.add(positionInDirection(direction));
        }
        return result;
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
