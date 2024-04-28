package org.openartifact.artifact.utils

fun randomFloat(range : IntRange) : Float =
    range.random().toFloat()

fun randomInt(range : IntRange) : Int =
    range.random()

fun randomLong(range : IntRange) : Long =
    range.random().toLong()

fun randomDouble(range : IntRange) : Double =
    range.random().toDouble()