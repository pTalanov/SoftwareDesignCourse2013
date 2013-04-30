package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public final class RecyclePoint extends ImmobileObject {
    public RecyclePoint(@NotNull Position position) {
        super(position, 'R');
    }
}
