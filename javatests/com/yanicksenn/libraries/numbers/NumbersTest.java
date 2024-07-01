package com.yanicksenn.libraries.numbers;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class NumbersTest {

    @Test
    public void parseInteger_withEmptyString_returnsEmpty() {
        assertThat(Numbers.parseInteger("")).isEmpty();
    }

    @Test
    public void parseInteger_withLetters_returnsEmpty() {
        assertThat(Numbers.parseInteger("ABC")).isEmpty();
    }

    @Test
    public void parseInteger_withNumbers_returnsPresent() {
        assertThat(Numbers.parseInteger("1234")).hasValue(1234);
    }


    @Test
    public void parseLong_withEmptyString_returnsEmpty() {
        assertThat(Numbers.parseLong("")).isEmpty();
    }

    @Test
    public void parseLong_withLetters_returnsEmpty() {
        assertThat(Numbers.parseLong("ABC")).isEmpty();
    }

    @Test
    public void parseLong_withNumbers_returnsPresent() {
        assertThat(Numbers.parseLong("1234")).hasValue(1234L);
    }
}
