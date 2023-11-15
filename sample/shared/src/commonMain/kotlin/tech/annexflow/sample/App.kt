package tech.annexflow.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.PreComposeApp
import tech.annexflow.sample.navigation.AppNavigation

@Composable
internal fun App() = MaterialTheme {
    PreComposeApp {
        Box(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .background(MaterialTheme.colorScheme.background)
        ) {
            AppNavigation()
        }
    }
}
