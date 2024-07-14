package com.yanicksenn.libraries.ranges;

public final class LongRange extends Range<Long> {
    public static LongRange of(long minInclusive, long maxExclusive) {
        return new LongRange(minInclusive, maxExclusive);
    }

    private LongRange(long minInclusive, long maxExclusive) {
        super(minInclusive, maxExclusive);
    }

    @Override
    public boolean contains(Long value) {
        if (value == null) {
            return false;
        }
        return value >= getMinInclusive() && value < getMaxExclusive();
    }

    @Override
    public Long size() {
        return getMaxExclusive() - getMinInclusive();
    }
}
