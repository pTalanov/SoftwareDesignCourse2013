package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public final class Drunkard implements Movable, Actor {

    private static final int TURNS_UNCONSCIOUS = 5;

    private int sleepDuration = 0;


    public Drunkard(@NotNull Position position) {
        this.position = position;
    }

    @NotNull
    private Position position;

    @NotNull
    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(@NotNull Position position) {
        this.position = position;
    }

    @Override
    public char representation() {
        return 'D';
    }

    @Override
    public String toString() {
        return String.valueOf(representation());
    }

    @Override
    public void performMove(@NotNull Board board) {
        if (isAsleep()) {
            sleepTurn();
        }
        Position randomMove = getPosition().randomAdjacentPosition();
        if (!board.isValid(randomMove)) {
            return;
        }
        if (!board.isEmpty(randomMove)) {
            fallAsleep();
            return;
        }
        board.move(this, randomMove);
    }


    private void fallAsleep() {
        sleepDuration = TURNS_UNCONSCIOUS;
    }

    private boolean isAsleep() {
        return sleepDuration > 0;
    }

    private void sleepTurn() {
        --sleepDuration;
    }
}
