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
        WALKING,
        LYING
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
        if (!canMove()) {
            return;
        }
        Position randomMove = getPosition().randomAdjacentPosition();
        if (!board.isValid(randomMove)) {
            return;
        }
        if (!board.isEmpty(randomMove)) {
            mayBeChangeStatus(board, randomMove);
            return;
        }
        doMove(board, randomMove);
    }

    private void mayBeChangeStatus(@NotNull Board board, @NotNull Position desiredDirection) {
        BoardObject obstacle = board.getObject(desiredDirection);
        if (obstacle instanceof Column ||
                (obstacle instanceof Drunkard && ((Drunkard) obstacle).state == State.SLEEPING)) {
            System.out.println("Column!");
            state = State.SLEEPING;
        } else if (obstacle instanceof Bottle) {
            System.out.println("Bottle!");
            board.setEmpty(desiredDirection);
            board.move(this, desiredDirection);
            state = State.LYING;
        }
    }

    private void doMove(@NotNull Board board, @NotNull Position randomMove) {
        Position oldPosition = getPosition();
        board.move(this, randomMove);
        assert board.isEmpty(oldPosition);
        if (drunkardDropsBottle()) {
            board.addObject(new Bottle(oldPosition));
        }
    }

    private boolean drunkardDropsBottle() {
        return RANDOM.nextInt(30) == 0;
    }

    private boolean canMove() {
        return state == State.WALKING;
    }
}
