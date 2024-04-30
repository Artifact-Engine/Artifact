package org.openartifact.artifact.core

/**
 * Fields annotated with [MutableExport] will be shown in the editor.
 * A field annotated with [MutableExport] has to be a var not a val.
 *
 * Below is an example for how to use [MutableExport]:
 *
 * ```kotlin
 * @Export
 * var exportedExampleVariable = 42
 * ```
 */
@Target(AnnotationTarget.FIELD)
annotation class MutableExport

/**
 * Fields annotated with [ImmutableExport] will be shown in the editor.
 * A field annotated with [ImmutableExport] can not be edited.
 *
 * Below is an example for how to use [ImmutableExport]:
 *
 * ```kotlin
 * @ImmutableExport
 * val exportedExampleVariable = 42
 * ```
 */
@Target(AnnotationTarget.FIELD)
annotation class ImmutableExport
