package org.openartifact.scripting.event

/**
 * An interface for objects that want to be notified of events.
 *
 */
interface EventListener {

    /**
     * Handles the given event.
     *
     * @param event the event to handle
     */
    suspend fun handle(event: Event)

    /**
     * The priority of the event listener (sorted by descending)
     */
    val priority: Int

}