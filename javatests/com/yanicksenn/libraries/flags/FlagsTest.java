package com.yanicksenn.libraries.flags;

import static com.google.common.truth.Truth.assertThat;

import com.yanicksenn.libraries.flags.Flags;
import java.util.List;
import org.junit.Test;

public class FlagsTest {

  @Test
  public void get_whenUnset_returnsEmpty() {
    parseFlags("");
    assertThat(Flags.get("value")).isEmpty();
  }

  @Test
  public void get_whenSetWithNoValue_returnsPresent() {
    parseFlags("--value");
    assertThat(Flags.get("value")).isEmpty();
  }

  @Test
  public void get_whenSetWithEmptyString_returnsPresent() {
    parseFlags("--value=");
    assertThat(Flags.get("value")).hasValue("");
  }

  @Test
  public void get_whenSetWithAnyString_returnsPresent() {
    parseFlags("--value=ABC");
    assertThat(Flags.get("value")).hasValue("ABC");
  }

  @Test
  public void getAsInteger_whenUnset_returnsEmpty() {
    parseFlags("");
    assertThat(Flags.getAsInteger("value")).isEmpty();
  }

  @Test
  public void getAsInteger_whenSetWithNoValue_returnsEmpty() {
    parseFlags("--value");
    assertThat(Flags.getAsInteger("value")).isEmpty();
  }

  @Test
  public void getAsInteger_whenSetWithEmptyString_returnsEmpty() {
    parseFlags("--value=");
    assertThat(Flags.getAsInteger("value")).isEmpty();
  }

  @Test
  public void getAsInteger_whenSetWithInvalidString_returnsEmpty() {
    parseFlags("--value=ABC");
    assertThat(Flags.getAsInteger("value")).isEmpty();
  }

  @Test
  public void getAsInteger_whenSetWithNumericString_returnsPresent() {
    parseFlags("--value=123");
    assertThat(Flags.getAsInteger("value")).hasValue(123);
  }

  @Test
  public void getAsLong_whenUnset_returnsEmpty() {
    parseFlags("");
    assertThat(Flags.getAsLong("value")).isEmpty();
  }

  @Test
  public void getAsLong_whenSetWithNoValue_returnsEmpty() {
    parseFlags("--value=");
    assertThat(Flags.getAsLong("value")).isEmpty();
  }

  @Test
  public void getAsLong_whenSetWithEmptyString_returnsEmpty() {
    parseFlags("--value=");
    assertThat(Flags.getAsLong("value")).isEmpty();
  }

  @Test
  public void getAsLong_whenSetWithNonNumericString_returnsEmpty() {
    parseFlags("--value=ABC");
    assertThat(Flags.getAsLong("value")).isEmpty();
  }

  @Test
  public void getAsLong_whenSetWithNumericString_returnsPresent() {
    parseFlags("--value=123");
    assertThat(Flags.getAsLong("value")).hasValue(123L);
  }

  @Test
  public void getAsEnum_whenUnset_returnsEmpty() {
    parseFlags("");
    assertThat(Flags.getAsEnum("value", Color.class)).isEmpty();
  }

  @Test
  public void getAsEnum_whenSetWithNoValue_returnsEmpty() {
    parseFlags("--value");
    assertThat(Flags.getAsEnum("value", Color.class)).isEmpty();
  }

  @Test
  public void getAsEnum_whenSetWithEmptyString_returnsEmpty() {
    parseFlags("--value=");
    assertThat(Flags.getAsEnum("value", Color.class)).isEmpty();
  }

  @Test
  public void getAsEnum_whenSetWithInvalidString_returnsEmpty() {
    parseFlags("--value=ABC");
    assertThat(Flags.getAsEnum("value", Color.class)).isEmpty();
  }

  @Test
  public void getAsEnum_whenSetWithEnumValueString_returnsPresent() {
    parseFlags("--value=RED");
    assertThat(Flags.getAsEnum("value", Color.class)).hasValue(Color.RED);
  }

  @Test
  public void isSet_whenUnset_returnsFalse() {
    parseFlags("");
    assertThat(Flags.isSet("value")).isFalse();
  }

  @Test
  public void isSet_whenSetWithValue_returnsTrue() {
    parseFlags("--value");
    assertThat(Flags.isSet("value")).isTrue();
  }

  @Test
  public void isSet_whenSetWithEmptyString_returnsTrue() {
    parseFlags("--value=");
    assertThat(Flags.isSet("value")).isTrue();
  }

  @Test
  public void isSet_whenSetWithAnyString_returnsTrue() {
    parseFlags("--value=ABC");
    assertThat(Flags.isSet("value")).isTrue();
  }

  @Test
  public void hasValue_whenUnset_returnsFalse() {
    parseFlags("");
    assertThat(Flags.hasValue("value")).isFalse();
  }

  @Test
  public void hasValue_whenSetWithValue_returnsFalse() {
    parseFlags("--value");
    assertThat(Flags.hasValue("value")).isFalse();
  }

  @Test
  public void hasValue_whenSetWithEmptyString_returnsTrue() {
    parseFlags("--value=");
    assertThat(Flags.hasValue("value")).isTrue();
  }

  @Test
  public void hasValue_whenSetWithAnyString_returnsTrue() {
    parseFlags("--value=ABC");
    assertThat(Flags.hasValue("value")).isTrue();
  }

  private static void parseFlags(String args) {
    Flags.init(args.split("\\s+"));
  }

  private enum Color {
    RED,
    GREEN,
    BLUE;
  }
}
