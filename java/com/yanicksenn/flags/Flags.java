package com.yanicksenn.flags;

import com.yanicksenn.numbers.Numbers;
import java.lang.Enum;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/** Utility class for managing flags. */
public final class Flags {

  private static final HashMap<String, String> parsedArgs = new HashMap<>();
  private static boolean initialized = false;

  /** Initializes and caches the given arguments. */
  public static void init(String[] args) {
    if (initialized) {
      throw new AssertionError("Flags already initialized");
    }

    for (String arg : args) {
      if (arg.startsWith("--")) {
        int separatorIndex = arg.indexOf("=");
        boolean hasValue = separatorIndex != -1;
        String argName = arg.substring(2, hasValue ? separatorIndex : arg.length());
        String argValue = hasValue ? arg.substring(separatorIndex + 1, arg.length()) : null;
        parsedArgs.put(argName, argValue);
      }
    }

    initialized = true;
  }

  /**
   * Returns the value to the given argument name. Returns empty if no argument with the given name
   * was set.
   */
  public static Optional<String> get(String name) {
    Objects.requireNonNull(name);
    return Optional.ofNullable(parsedArgs.get(name));
  }

  /**
   * Returns the integer value to the given argument name. Returns empty if no argument with the
   * given name was set or when it can't be parsed to an integer.
   */
  public static Optional<Integer> getAsInteger(String name) {
    Objects.requireNonNull(name);
    return Numbers.parseInteger(parsedArgs.get(name));
  }

  /**
   * Returns the long value to the given argument name. Returns empty if no argument with the given
   * name was set or when it can't be parsed to an long.
   */
  public static Optional<Long> getAsLong(String name) {
    Objects.requireNonNull(name);
    return Numbers.parseLong(parsedArgs.get(name));
  }

  /**
   * Returns the enum value to the given argument name. Returns empty if no argument with the given
   * name was set or when it can't be parsed to an existing enum value.
   */
  public static <T extends Enum<T>> Optional<T> getAsEnum(String name, Class<T> enumClass) {
    Objects.requireNonNull(name);
    try {
      return Optional.of(Enum.valueOf(enumClass, parsedArgs.get(name)));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

  /** Returns wether a flag with the given name is set. A flag may have no value but is set. */
  public static boolean isSet(String name) {
    Objects.requireNonNull(name);
    return parsedArgs.containsKey(name);
  }

  /**
   * Returns whether a flag with the given name has a value. An unset flag has no value. A flag may
   * be set but has no value.
   */
  public static boolean hasValue(String name) {
    Objects.requireNonNull(name);
    return isSet(name) && parsedArgs.get(name) != null;
  }

  private Flags() {
    throw new AssertionError();
  }
}
