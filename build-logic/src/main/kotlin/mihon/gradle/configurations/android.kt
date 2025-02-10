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

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import mihon.gradle.extensions.coreLibraryDesugaring
import mihon.gradle.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType

private object AndroidConstants {
    const val COMPILE_SDK = 35
    const val MIN_SDK = 26
    const val TARGET_SDK = 35
}

fun Project.configureAndroid() {
    android {
        compileSdk = AndroidConstants.COMPILE_SDK

        defaultConfig {
            minSdk = AndroidConstants.MIN_SDK
            if (this is ApplicationDefaultConfig) {
                targetSdk = AndroidConstants.TARGET_SDK
            }
        }

        compileOptions {
            isCoreLibraryDesugaringEnabled = true
        }
    }

    dependencies {
        coreLibraryDesugaring(libs.android.desugar)
    }
}

private fun Project.android(action: CommonExtension<*, *, *, *, *, *>.() -> Unit) {
    extensions.findByType<BaseExtension>()
        ?.let { it as? CommonExtension<*, *, *, *, *, *> }
        ?.action()
}
