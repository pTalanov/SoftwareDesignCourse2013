package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Pavel Talanov
 */
public final class Simulation {

    @NotNull
    private final PrintStream resultOut;
    @NotNull
    private final PrintStream debugOut;

    private static final int MAX_MOVES = 200;

    @NotNull
    private final Board board;
    @NotNull
    private final Collection<Actor> actors;


    public Simulation(@NotNull PrintStream resultOut, @NotNull PrintStream debugOut) {
        this.resultOut = resultOut;
        this.debugOut = debugOut;
        this.board = new Board(15);
        Drunkard drunkard = new Drunkard(new Position(0, 0));
        this.actors = Arrays.<Actor>asList(drunkard);
        this.board.addObject(drunkard);
        board.addObject(new Column(new Position(7, 7)));
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
        for (Actor actor : actors) {
            actor.performMove(board);
        }
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
