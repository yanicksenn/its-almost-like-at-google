package com.yanicksenn.libraries.ranges;

import java.util.Objects;
import java.util.stream.Stream;

public abstract class Range<T extends Number> {
    private final T minInclusive;
    private final T maxExclusive;

    Range(T minInclusive, T maxExclusive) {
        this.minInclusive = minInclusive;
        this.maxExclusive = maxExclusive;
    }

    public abstract Stream<T> stream();

    public abstract boolean contains(T value);

    public abstract T size();

    public T getMinInclusive() {
        return minInclusive;
    }

    public T getMaxExclusive() {
        return maxExclusive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range<?> range = (Range<?>) o;
        return Objects.equals(getMinInclusive(), range.getMinInclusive())
            && Objects.equals(getMaxExclusive(), range.getMaxExclusive());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMinInclusive(), getMaxExclusive());
    }
}
