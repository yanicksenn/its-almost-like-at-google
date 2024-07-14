package com.yanicksenn.games.vaultmanager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.yanicksenn.games.vaultmanager.Game.Module.Annotations.Seed;
import com.yanicksenn.libraries.ranges.IntRange;
import com.yanicksenn.libraries.ranges.LongRange;

import java.util.List;

@Singleton
public final class Random {
    private final java.util.Random random;

    @Inject
    Random(@Seed long seed) {
        random = new java.util.Random(seed);
    }

    public <T> T from(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("list must not be empty");
        }
        return list.get(inRange(IntRange.until(0, list.size())));
    }

    public int between(int min, int max) {
        return inRange(IntRange.between(min, max));
    }

    public int inRange(IntRange range) {
        return range.getMinInclusive() + random.nextInt(range.getMaxExclusive() - range.getMinInclusive());
    }

    public long inRange(LongRange range) {
        return range.getMinInclusive() + random.nextLong(range.getMaxExclusive() - range.getMinInclusive());
    }
}
