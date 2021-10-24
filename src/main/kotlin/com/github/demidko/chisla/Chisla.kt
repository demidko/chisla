package com.github.demidko.chisla

import com.github.demidko.aot.PartOfSpeech.Numeral
import com.github.demidko.aot.PartOfSpeech.OrdinalNumber
import com.github.demidko.aot.WordformMeaning.lookupForMeanings
import com.github.demidko.tokenizer.tokenize

private val numbers = Numbers()
private val separators = setOf("целых", "плюс", "да", "и", ",", ".")

fun String.parseRussianDouble() = tokenize().parseRussianDouble()

fun List<String>.parseRussianDouble(): Double {
  val integerPart = mutableListOf<Double>()
  val fractionalPart = mutableListOf<Double>()
  var currentPart = integerPart
  for (token in this) {
    if (integerPart.isNotEmpty() && token.lowercase() in separators) {
      currentPart = fractionalPart
      continue
    }
    val number =
      lookupForMeanings(token)
        .run {
          firstOrNull { it.partOfSpeech == Numeral || it.partOfSpeech == OrdinalNumber }
            ?: getOrNull(0)
        }
        ?.lemma
        ?.toString()
        ?.let(numbers::get)
    if (number != null) {
      currentPart += number
      continue
    }
    if (currentPart.isNotEmpty()) {
      break
    }
  }
  return integerPart.join() + fractionalPart.join()
}

private fun List<Double>.join(): Double {
  if (isEmpty()) {
    return 0.0
  }
  var result = 0.0
  var token = 0.0
  for (number in this) {
    if (number in numbers.powersOfTen) {
      result += token * number
      token = 0.0
      continue
    }
    token += number
  }
  return result + token
}

