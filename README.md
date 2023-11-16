[![Maven Central](https://img.shields.io/maven-central/v/tech.annexflow.precompose/precompose-navigation-typesafe)](https://search.maven.org/search?q=g:tech.annexflow.precompose)
![license](https://img.shields.io/github/license/Lavmee/precompose-navigation-typesafe)

![badge-Android](https://img.shields.io/badge/Platform-Android-brightgreen)
![badge-iOS](https://img.shields.io/badge/Platform-iOS-lightgray)
![badge-JVM](https://img.shields.io/badge/Platform-JVM-orange)
![badge-macOS](https://img.shields.io/badge/Platform-macOS-purple)

## PreCompose Navigation TypeSafe

## Usage

Usage is very simple:

```kotlin
import kotlinx.serialization.Serializable
import tech.annexflow.precompose.navigation.typesafe.Route

sealed interface AppRoutes : Route {
    @Serializable
    data object Home : AppRoutes

    @Serializable
    data class Scene(val id: Int) : AppRoutes
}
```

```kotlin
@Composable
fun AppNavigation() {
    val navigator = rememberNavigator()
    TypesafeNavHost(
        initialRoute = AppRoutes.Home,
        navigator = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        scene<AppRoutes.Home> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Home")
                Button(
                    onClick = {
                        navigator.navigate(route = AppRoutes.Scene(id = 123))
                    }
                ) {
                    Text("Go to scene #123")
                }
            }
        }
        scene<AppRoutes.Scene> {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Scene: $id")
                Button(
                    onClick = navigator::goBack
                ) {
                    Text("Back")
                }
            }
        }
    }
}
```

## Download

```kotlin
plugins {
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.20"
}

val commonMain by getting {
    dependencies {
        implementation("moe.tlaster:precompose:1.5.7")
        implementation("tech.annexflow.precompose:navigation-typesafe:0.2.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.1")
    }
}
```

## License
```
MIT License

Copyright (c) 2023 Lavmee

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
