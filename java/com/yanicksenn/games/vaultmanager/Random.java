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
        return list.get(between(IntRange.of(0, list.size())));
    }

    public int between(IntRange range) {
        return range.getMinInclusive() + random.nextInt(range.getMaxExclusive() - range.getMinInclusive());
    }

    public long between(LongRange range) {
        return range.getMinInclusive() + random.nextLong(range.getMaxExclusive() - range.getMinInclusive());
    }
}
