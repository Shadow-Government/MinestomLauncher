package com.thecrownstudios.minestomlauncher.util;

import org.jetbrains.annotations.Nullable;

public record ObjectTriple<LEFT, MID, RIGHT>(
        @Nullable LEFT  left,
        @Nullable MID   mid,
        @Nullable RIGHT right
) {

    /**
     * Creates a new type-specific immutable ObjectTriple
     * with given left, mid and right value.
     *
     * @param left                  the left-value.
     * @param mid                   the mid-value.
     * @param right                 the right-value.
     */
    public static <LEFT, MID, RIGHT> ObjectTriple<LEFT, MID, RIGHT> of(
            final @Nullable LEFT  left,
            final @Nullable MID   mid,
            final @Nullable RIGHT right)
    {
        return new ObjectTriple<>(left, mid, right);
    }

    public ObjectTriple {
        if (left == null && mid == null && right == null) {
            throw new NullPointerException("At least one of the elements must be not null");
        }
    }

}