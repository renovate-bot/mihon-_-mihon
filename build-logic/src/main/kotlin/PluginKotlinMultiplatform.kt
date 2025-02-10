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
import mihon.gradle.configurations.configureKotlin
import mihon.gradle.configurations.configureSpotless
import mihon.gradle.extensions.alias
import mihon.gradle.extensions.hasPlugin
import mihon.gradle.extensions.libs
import mihon.gradle.extensions.plugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class PluginKotlinMultiplatform : Plugin<Project> {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project): Unit = with(target) {
        plugins {
            alias(libs.plugins.kotlin.multiplatform)
        }

        kotlin {
            val androidPlugins = libs.plugins.android.let { listOf(it.application, it.library) }
            if (androidPlugins.any(pluginManager::hasPlugin)) {
                androidTarget()
            }
            jvm("desktop")
            iosX64()
            iosArm64()
            iosSimulatorArm64()
            applyDefaultHierarchyTemplate {
                common {
                    group("jvm") {
                        withAndroidTarget()
                        withJvm()
                    }
                    group("ios") {
                        withIosX64()
                        withIosArm64()
                        withIosSimulatorArm64()
                    }
                }
            }
        }

        configureKotlin()
        configureSpotless()
    }
}

private fun Project.kotlin(action: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure(action)
}
