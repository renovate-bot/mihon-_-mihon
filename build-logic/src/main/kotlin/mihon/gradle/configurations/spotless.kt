/*
 * Copyright (C) 2024-Present AntsyLich and Mihon Open Source Project contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package mihon.gradle.configurations

import com.diffplug.gradle.spotless.SpotlessExtension
import mihon.gradle.extensions.alias
import mihon.gradle.extensions.libs
import mihon.gradle.extensions.plugins
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureSpotless() {
    plugins {
        alias(libs.plugins.spotless)
    }

    // Configuration should be synced with [/build-logic/build.gradle.kts]
    spotless {
        val ktlintVersion = libs.ktlint.cli.get().version
        kotlin {
            target("src/**/*.kt")
            ktlint(ktlintVersion)
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint(ktlintVersion)
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"), "(^(?![\\/ ]\\**).*$)")
        }
    }
}

private fun Project.spotless(action: SpotlessExtension.() -> Unit) {
    extensions.configure(action)
}
