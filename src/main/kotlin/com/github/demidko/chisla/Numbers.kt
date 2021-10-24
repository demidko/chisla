package com.github.demidko.chisla

internal class Numbers(
  private val aliases: Map<String, Double>,
  val powersOfTen: Set<Double>
) : Map<String, Double> by aliases

internal fun Numbers(): Numbers {
  val decimalAliases = loadNumbersAliases("/decimal_numbers")
  val powersOfTen = decimalAliases.values.toSet()
  val allAliases = loadNumbersAliases("/other_numbers") + decimalAliases
  return Numbers(allAliases, powersOfTen)
}

private fun loadNumbersAliases(resourceName: String) =
  {}.javaClass.getResourceAsStream(resourceName)!!
    .bufferedReader()
    .readLines()
    .flatMap { line ->
      val aliases = line.split(' ')
      val number = aliases.first().toDouble()
      aliases.drop(1).map { Pair(it, number) }
    }.toMap()