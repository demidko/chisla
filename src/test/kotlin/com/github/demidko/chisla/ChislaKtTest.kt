package com.github.demidko.chisla

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class ChislaKtTest {

  @Test
  fun parseRussianDouble() {
    assertThat("Двенадцать тысяч шестьсот пятьдесят девять целых четыре миллионных".parseRussianDouble())
      .isEqualTo(12659.000004)
    assertThat("Десять тысяч четыреста тридцать четыре".parseRussianDouble())
      .isEqualTo(10434)
    assertThat("Двенадцать целых шестьсот пятьдесят девять тысячных".parseRussianDouble())
      .isEqualTo(12.659)
    assertThat("Ноль целых пятьдесят восемь сотых".parseRussianDouble())
      .isEqualTo(0.58)
    assertThat("Сто тридцать пять".parseRussianDouble())
      .isEqualTo(135)
  }
}