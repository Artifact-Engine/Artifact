package org.openartifact.artifact.extensions

fun <T> Any.to() : T =
    this as T
