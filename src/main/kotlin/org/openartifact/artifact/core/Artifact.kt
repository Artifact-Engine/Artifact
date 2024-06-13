/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.core

import org.openartifact.artifact.core.application.Application
import org.openartifact.artifact.graphics.window.Window
import org.openartifact.artifact.graphics.window.WindowConfig

class Artifact(val application: Application) {

    private lateinit var _window : Window

    fun init(windowConfig: WindowConfig) {
        _window = Window(windowConfig)
        _window.run()
    }

    val window: Window
        get() = _window

    companion object {
        @Volatile
        private var _instance: Artifact? = null

        val instance: Artifact
            get() = _instance?: synchronized(this) {
                _instance?: throw IllegalStateException("Artifact instance not initialized")
            }

        fun launch(application: Application): Artifact {
            return Artifact(application).also {
                _instance = it
                _instance!!.init(application.windowConfig)
            }
        }
    }
}
