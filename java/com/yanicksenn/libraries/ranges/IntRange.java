package com.yanicksenn.libraries.ranges;

public final class IntRange extends Range<Integer> {
    public static IntRange of(int minInclusive, int maxExclusive) {
        return new IntRange(minInclusive, maxExclusive);
    }

    private IntRange(int minInclusive, int maxExclusive) {
        super(minInclusive, maxExclusive);
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
