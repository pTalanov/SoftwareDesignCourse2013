package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Pavel Talanov
 */
public final class Drunkard implements Movable, Actor {

    public static final Random RANDOM = new Random();

    private enum State {
        SLEEPING,
        WALKING
    }

    @NotNull
    private State state = State.WALKING;

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
            return;
        }
        Position randomMove = getPosition().randomAdjacentPosition();
        if (!board.isValid(randomMove)) {
            return;
        }
        if (!board.isEmpty(randomMove)) {
            fallAsleep();
            return;
        }
        doMove(board, randomMove);
    }

    private void doMove(Board board, Position randomMove) {
        Position oldPosition = getPosition();
        board.move(this, randomMove);
        assert board.isEmpty(oldPosition);
        if (drunnkardDropsBottle()) {
            board.addObject(new Bottle(oldPosition));
        }
    }

    private boolean drunnkardDropsBottle() {
        return RANDOM.nextInt(30) == 0;
    }

    private void fallAsleep() {
        state = State.SLEEPING;
    }

    private boolean isAsleep() {
        return state == State.SLEEPING;
    }
}
