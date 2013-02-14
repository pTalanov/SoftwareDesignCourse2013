package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public final class Drunkard implements Movable {

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
}
