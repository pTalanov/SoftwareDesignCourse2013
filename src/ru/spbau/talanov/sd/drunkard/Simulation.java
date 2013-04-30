package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

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
    private final SimulationState state;


    public Simulation(@NotNull PrintStream resultOut, @NotNull PrintStream debugOut) {
        this.resultOut = resultOut;
        this.debugOut = debugOut;
        Board board = new Board(15);
        Drunkard drunkard = new Drunkard(new Position(0, 0));
        board.addObject(drunkard);
        board.addObject(new Column(new Position(7, 7)));
        board.addObject(new Lantern(new Position(10, 3)));
        Inn theInn = new Inn(new Position(9, -1), new Position(9, 0));
        board.addSpecialObject(theInn);
        this.state = SimulationState.initialState(board, Arrays.<Actor>asList(drunkard, theInn));

    }

    public void simulate() {
        for (int i = 0; i < MAX_MOVES; ++i) {
            debugOut.println(i);
            debugOut.println("-------------------------------------");
            debugOut.print(state.getBoard().representation());
            debugOut.println("-------------------------------------");
            makeMove();
        }
        resultOut.println(state.getBoard().representation());
    }

    private void makeMove() {
        for (Actor actor : state.getActors()) {
            actor.performMove(state);
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
