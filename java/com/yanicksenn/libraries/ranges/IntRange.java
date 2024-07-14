package com.yanicksenn.libraries.ranges;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class IntRange extends Range<Integer> {
    public static IntRange until(int maxExclusive) {
        return new IntRange(0, maxExclusive);
    }

    public static IntRange until(int minInclusive, int maxExclusive) {
        return new IntRange(minInclusive, maxExclusive);
    }

    public static IntRange to(int maxInclusive) {
        return new IntRange(0, maxInclusive + 1);
    }

    public static IntRange between(int minInclusive, int maxInclusive) {
        return new IntRange(minInclusive, maxInclusive + 1);
    }

    private IntRange(int minInclusive, int maxExclusive) {
        super(minInclusive, maxExclusive);
    }

    @Override
    public Stream<Integer> stream() {
        return IntStream.range(getMinInclusive(), getMaxExclusive()).boxed();
    }

    @Override
    public boolean contains(Integer value) {
        if (value == null) {
            return false;
        }
        return value >= getMinInclusive() && value < getMaxExclusive();
    }

    @Override
    public Integer size() {
        return getMaxExclusive() - getMinInclusive();
    }
}
