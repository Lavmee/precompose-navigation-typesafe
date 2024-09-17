import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.serialization)
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
kotlin {
    kotlin.applyDefaultHierarchyTemplate()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(rootProject.extra.get("jvmTarget") as String))
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(rootProject.extra.get("jvmTarget") as String))
        }
    }

    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs +=
                    listOf(
                        "-linker-option", "-framework", "-linker-option", "Metal",
                    )
            }
        }
    }
    macosX64 {
        binaries {
            executable {
                entryPoint = "main"
                freeCompilerArgs +=
                    listOf(
                        "-linker-option", "-framework", "-linker-option", "Metal",
                    )
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(libs.precompose)
                implementation(libs.kotlinx.serialization.core)
                implementation(project(":typesafe"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activityCompose)
                implementation(libs.compose.uitooling)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
            }
        }

        val iosMain by getting {
            dependencies {
            }
        }
    }
}

android {
    namespace = "tech.annexflow.sample"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        applicationId = "tech.annexflow.sample.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(rootProject.extra.get("jvmTarget") as String)
        targetCompatibility = JavaVersion.toVersion(rootProject.extra.get("jvmTarget") as String)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "tech.annexflow.sample.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

compose.desktop.nativeApplication {
    targets(kotlin.targets.getByName("macosX64"), kotlin.targets.getByName("macosArm64"))
    distributions {
        targetFormats(TargetFormat.Dmg)
        packageName = "PreComposeNavigationTypeSafeSample"
        packageVersion = "1.0.0"
    }
}
