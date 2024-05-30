package com.yanicksenn.numbers;

import com.yanicksenn.functions.ThrowingSupplier;
import java.lang.NumberFormatException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Numbers {

    public static Optional<Integer> parseInteger(String value) {
        return wrapOrEmptyOnThrow(() -> Integer.parseInt(value));
    }

    public static Optional<Long> parseLong(String value) {
        return wrapOrEmptyOnThrow(() -> Long.parseLong(value));
    }

    private static <T> Optional<T> wrapOrEmptyOnThrow(ThrowingSupplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Numbers() {
        throw new AssertionError();
    }
}