package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Pavel Talanov
 */
public final class Simulation {

    @NotNull
    private final PrintStream resultOut;
    @NotNull
    private final PrintStream debugOut;

    private static final int MAX_MOVES = 200;
    private static final int TURNS_UNCONSCIOUS = 5;

    @NotNull
    private Drunkard drunkard;
    @NotNull
    private Board board;

    private int drunkardSleepDuration = 0;

    public Simulation(@NotNull PrintStream resultOut, @NotNull PrintStream debugOut) {
        this.resultOut = resultOut;
        this.debugOut = debugOut;
        this.board = new Board(15);
        drunkard = new Drunkard(new Position(0, 0));
        this.board.addObject(drunkard);
        final Position obstaclePosition = new Position(7, 7);
        BoardObject obstacle = new BoardObject() {
            @NotNull
            @Override
            public Position getPosition() {
                return obstaclePosition;
            }

            @Override
            public char representation() {
                return 'I';
            }
        };
        board.addObject(obstacle);
    }

    public void simulate() {
        for (int i = 0; i < MAX_MOVES; ++i) {
            debugOut.println(i);
            debugOut.println("-------------------------------------");
            debugOut.print(board.representation());
            debugOut.println("-------------------------------------");
            makeMove();
        }
        resultOut.println(board.representation());
    }

    private void makeMove() {
        if (isAsleep()) {
            sleepTurn();
        }
        Position randomMove = drunkard.getPosition().randomAdjacentPosition();
        if (!board.isValid(randomMove)) {
            return;
        }
        if (!board.isEmpty(randomMove)) {
            fallAsleep();
            return;
        }
        board.move(drunkard, randomMove);
    }

    private void fallAsleep() {
        drunkardSleepDuration = TURNS_UNCONSCIOUS;
    }

    private boolean isAsleep() {
        return drunkardSleepDuration > 0;
    }

    private void sleepTurn() {
        --drunkardSleepDuration;
    }

    public static void main(String[] args) {
        try (FileOutputStream debugFileStream = new FileOutputStream("debug.out")) {
            try (PrintStream debugPrintStream = new PrintStream(debugFileStream)) {
                Simulation simulation = new Simulation(System.out, debugPrintStream);
                simulation.simulate();
            }
        } catch (IOException e) {
            System.out.println("Error while writing debug file.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
