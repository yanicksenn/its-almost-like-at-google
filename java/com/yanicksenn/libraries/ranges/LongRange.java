package com.yanicksenn.libraries.ranges;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class LongRange extends Range<Long> {
    public static LongRange until(long maxExclusive) {
        return new LongRange(0, maxExclusive);
    }

    public static LongRange until(long minInclusive, long maxExclusive) {
        return new LongRange(minInclusive, maxExclusive);
    }

    public static LongRange to(long maxInclusive) {
        return new LongRange(0, maxInclusive + 1);
    }

    public static LongRange between(long minInclusive, long maxInclusive) {
        return new LongRange(minInclusive, maxInclusive + 1);
    }

    private LongRange(long minInclusive, long maxExclusive) {
        super(minInclusive, maxExclusive);
    }

    @Override
    public Stream<Long> stream() {
        return LongStream.range(getMinInclusive(), getMaxExclusive()).boxed();
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
