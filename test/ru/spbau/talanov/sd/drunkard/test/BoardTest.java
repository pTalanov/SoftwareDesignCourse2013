package ru.spbau.talanov.sd.drunkard.test;

import org.junit.Test;
import ru.spbau.talanov.sd.drunkard.Board;
import ru.spbau.talanov.sd.drunkard.Drunkard;
import ru.spbau.talanov.sd.drunkard.Position;

import static junit.framework.Assert.assertEquals;

/**
 * @author Pavel Talanov
 */
public final class BoardTest {

    @Test
    public void emptyBoard() {
        assertEquals("OOO\nOOO\nOOO\n", new Board(3).representation());
    }

    @Test
    public void drunkardMoves() {
        Board board = new Board(3);
        Drunkard drunkard = new Drunkard(new Position(2, 1));
        board.addObject(drunkard);
        assertEquals("OOO\nOOD\nOOO\n", board.representation());
        board.move(drunkard, new Position(0, 0));
        assertEquals("OOO\nOOO\nDOO\n", board.representation());
    }
}
