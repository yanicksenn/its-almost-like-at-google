package com.yanicksenn.flags;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public final class Flags {

  private static final HashMap<String, String> parsedArgs = new HashMap<>();

  /** Initializes and caches the given arguments. */
  public static void init(String[] args) {
    parsedArgs.clear();
    for (String arg : args) {
      if (arg.startsWith("--")) {
        String[] parts = arg.split("=");
        String name = parts[0].substring(2);
        if (parts.length == 2) {
          String value = parts[1];
          parsedArgs.put(name, value);
        } else {
          parsedArgs.put(name, Boolean.TRUE.toString());
        }
      }
    }
  }

  /**
   * Returns the value to the given argument name. Returns empty if no argument with the given name
   * was set.
   */
  public static Optional<String> get(String name) {
    Objects.requireNonNull(name);
    return Optional.ofNullable(parsedArgs.get(name));
  }

  private Flags() {
    throw new AssertionError();
  }
}
