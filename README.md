# Chisla

Java/Kotlin библиотека для распознавания чисел из текста (слов русского языка).

## Как подключить?

Вам понадобится Gradle, Maven, или другая система сборки.

[![](https://jitpack.io/v/demidko/chisla.svg)](https://jitpack.io/#demidko/chisla)

## Как пользоваться?

Вам достаточно импортировать функцию `parseRussianDouble`. Она конвертирует число из прописи
в `Double`. 

```kotlin
import com.github.demidko.chisla.parseRussianDouble

fun main() {
  "Двенадцать тысяч шестьсот пятьдесят девять целых четыре миллионных"
    .parseRussianDouble() 
    // 12_659.000_004
}
```










