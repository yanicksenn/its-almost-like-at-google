package com.yanicksenn.libraries.numbers;

import com.yanicksenn.libraries.functions.ThrowingSupplier;
import java.util.Optional;

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
