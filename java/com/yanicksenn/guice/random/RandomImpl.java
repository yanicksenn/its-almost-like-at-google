package com.yanicksenn.guice.random;

import com.google.inject.*;
import com.yanicksenn.libraries.ranges.IntRange;
import com.yanicksenn.libraries.ranges.LongRange;

import java.util.List;
import java.util.Optional;

@Singleton
public final class RandomImpl implements Random {
    private final Optional<Long> seed;
    private final java.util.Random random;

    @Inject
    RandomImpl(@Annotations.InitialSeed Optional<Long> seed) {
        this.seed = seed;
        this.random = new java.util.Random();

        seed.ifPresent(this::setSeed);
    }

    @Override
    public void setSeed(long seed) {
        random.setSeed(seed);
    }

    @Override
    public <T> T from(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("list must not be empty");
        }
        return list.get(inRange(IntRange.until(0, list.size())));
    }

    @Override
    public int between(int min, int max) {
        return inRange(IntRange.between(min, max));
    }

    @Override
    public int inRange(IntRange range) {
        return range.getMinInclusive() + random.nextInt(range.getMaxExclusive() - range.getMinInclusive());
    }

    @Override
    public long inRange(LongRange range) {
        return range.getMinInclusive() + random.nextLong(range.getMaxExclusive() - range.getMinInclusive());
    }
}
