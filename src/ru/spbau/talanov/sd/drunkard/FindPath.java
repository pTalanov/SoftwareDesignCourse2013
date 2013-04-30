package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author Pavel Talanov
 */
public final class FindPath {

    @Nullable
    public static List<Position> findPath(@NotNull Position from,
                                          @NotNull PositionPredicate validPosition,
                                          @NotNull PositionPredicate destination) {
        return new FindPath(from, validPosition, destination).performBFS();
    }

    private FindPath(@NotNull Position from,
                     @NotNull PositionPredicate validPosition,
                     @NotNull PositionPredicate destinationPredicate) {
        this.from = from;
        this.validPosition = validPosition;
        this.destinationPredicate = destinationPredicate;
    }

    public interface PositionPredicate {
        boolean accepts(@NotNull Position position);
    }

    @NotNull
    private final Queue<Position> positionQueue = new LinkedList<>();
    @NotNull
    private final Map<Position, Position> previousPosition = new HashMap<>();
    @NotNull
    private final Position from;
    @NotNull
    private final PositionPredicate validPosition;
    @NotNull
    private final PositionPredicate destinationPredicate;

    @Nullable
    private List<Position> performBFS() {
        positionQueue.add(from);
        previousPosition.put(from, null);
        while (!positionQueue.isEmpty()) {
            Position current = positionQueue.poll();
            for (Position adjacentPosition : current.adjacentPositions()) {
                if (!isVisited(adjacentPosition) &&
                        (validPosition.accepts(adjacentPosition) || destinationPredicate.accepts(adjacentPosition))) {
                    previousPosition.put(adjacentPosition, current);
                    positionQueue.add(adjacentPosition);
                    if (destinationPredicate.accepts(adjacentPosition)) {
                        return restorePath(adjacentPosition);
                    }
                }
            }
        }
        return null;
    }

    private boolean isVisited(@NotNull Position adjacentPosition) {
        return previousPosition.containsKey(adjacentPosition);
    }

    @NotNull
    private List<Position> restorePath(@NotNull Position destination) {
        LinkedList<Position> path = new LinkedList<>();
        Position current = destination;
        while (current != null) {
            path.addFirst(current);
            current = previousPosition.get(current);
        }
        return path;
    }

}