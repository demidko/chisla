package com.github.demidko.chisla

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.ibm.icu.text.RuleBasedNumberFormat
import com.ibm.icu.text.RuleBasedNumberFormat.SPELLOUT
import org.junit.jupiter.api.Test
import java.util.*


internal class ChislaKtTest {

  @Test
  fun parseRussianDouble() {
    assertThat("Двенадцать тысяч шестьсот пятьдесят девять целых четыре миллионных".parseRussianDouble())
      .isEqualTo(12659.000004)

    assertThat("Десять тысяч четыреста тридцать четыре".parseRussianDouble())
      .isEqualTo(10434.0)

    assertThat("Двенадцать целых шестьсот пятьдесят девять тысячных".parseRussianDouble())
      .isEqualTo(12.659)

    assertThat("Ноль целых пятьдесят восемь сотых".parseRussianDouble())
      .isEqualTo(0.58)

    assertThat("Нуль целых пятьдесят восемь".parseRussianDouble())
      .isEqualTo(0.5800000000000001)

    assertThat("Сто тридцать пять".parseRussianDouble())
      .isEqualTo(135.0)
  }

  @Test
  fun parseIcuDouble() {
    val ru = Locale("ru", "ru")
    val parseIcu: String.() -> Number = RuleBasedNumberFormat(ru, SPELLOUT)::parse
    assertThat("двенадцать тысяч шестьсот пятьдесят девять целых четыре миллионных".parseIcu())
      .isEqualTo(12659.000004)

    assertThat("десять тысяч четыреста тридцать четыре".parseIcu())
      .isEqualTo(10434L)

    assertThat("двенадцать целых шестьсот пятьдесят девять тысячных".parseIcu())
      .isEqualTo(12.659)

    assertThat("ноль целых пятьдесят восемь сотых".parseIcu())
      .isEqualTo(0.58)

    /*assertThat("ноль целых пятьдесят восемь".parseIcu())
      .isEqualTo(0.58)*/

    assertThat("сто тридцать пять".parseIcu())
      .isEqualTo(135L)
  }
}