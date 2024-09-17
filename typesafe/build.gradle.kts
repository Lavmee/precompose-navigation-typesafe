import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.multiplatform)
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
kotlin {
    kotlin.applyDefaultHierarchyTemplate()

    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(rootProject.extra.get("jvmTarget") as String))
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(rootProject.extra.get("jvmTarget") as String))
        }
    }

    js(IR) {
        browser()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    macosArm64()
    macosX64()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "typesafe"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.animation)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.precompose)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "tech.annexflow.precompose.navigation.typesafe"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(rootProject.extra.get("jvmTarget") as String)
        targetCompatibility = JavaVersion.toVersion(rootProject.extra.get("jvmTarget") as String)
    }
}
