package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Pavel Talanov
 */
public final class Simulation {

    @NotNull
    private final PrintStream resultOut;
    @NotNull
    private final PrintStream debugOut;

    private static final List<Integer> movesToReport = Arrays.asList(200, 300, 500);

    private static final int MAX_MOVES = Collections.max(movesToReport);

    @NotNull
    private final SimulationState state;


    public Simulation(@NotNull PrintStream resultOut, @NotNull PrintStream debugOut) {
        this.resultOut = resultOut;
        this.debugOut = debugOut;
        Board board = new Board(15);
        board.addObject(new Column(new Position(7, 7)));
        Lantern lantern = new Lantern(new Position(10, 3));
        board.addObject(lantern);
        Inn theInn = new Inn(new Position(9, -1), new Position(9, 0));
        board.addSpecialObject(theInn);
        PoliceStation policeStation = new PoliceStation(new Position(15, 3));
        board.addSpecialObject(policeStation);
        Policeman policeman = new Policeman(new Position(14, 3), policeStation.getPosition(), lantern.getPosition());
        this.state = SimulationState.initialState(board, Arrays.<Actor>asList(theInn, policeman));

    }

    public void simulate() {
        for (int i = 0; i <= MAX_MOVES; ++i) {
            output(i, debugOut);
            makeMove();
            if (movesToReport.contains(i)) {
                output(i, resultOut);
            }
        }
    }

    private void output(int i, PrintStream out) {
        out.println(i);
        out.println("-------------------------------------");
        out.print(state.getBoard().representation());
        out.println("-------------------------------------");
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
        }
    }
}
