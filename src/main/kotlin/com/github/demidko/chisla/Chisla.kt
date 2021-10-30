package com.github.demidko.chisla

import com.github.demidko.aot.PartOfSpeech.Numeral
import com.github.demidko.aot.PartOfSpeech.OrdinalNumber
import com.github.demidko.aot.WordformMeaning.lookupForMeanings
import com.github.demidko.tokenizer.tokenize

private val separators = setOf("целых", "плюс", "да", "и", ",", ".")

private val numbers =
  {}.javaClass.getResourceAsStream("/dictionary")!!
    .bufferedReader()
    .readLines()
    .flatMap { line ->
      val aliases = line.split(' ')
      val number = aliases.first().toDouble()
      aliases.drop(1).map { Pair(it, number) }
    }.toMap()

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
  if (fractionalPart.isEmpty()) {
    return integerPart.join()
  }
  if (fractionalPart.last() < 1) {
    return integerPart.join() + fractionalPart.dropLast(1).join() * fractionalPart.last()
  }
  var fractionalNumber = fractionalPart.join()
  while (fractionalNumber > 1) {
    fractionalNumber *= 0.1
  }
  return integerPart.join() + fractionalNumber
}

private fun List<Double>.join(): Double {
  var tokensSum = 0.0
  var previousToken = first()
  for (currToken in drop(1)) {
    if (currToken > previousToken) {
      previousToken *= currToken
    } else {
      tokensSum += previousToken
      previousToken = currToken
    }
  }
  return tokensSum + previousToken
}

