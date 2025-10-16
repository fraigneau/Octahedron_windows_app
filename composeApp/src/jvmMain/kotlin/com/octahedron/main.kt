package com.octahedron

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import com.octahedron.ui.App


@OptIn(ExperimentalMaterial3Api::class)
fun main() = application {

    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        position = WindowPosition.Aligned(Alignment.Center),
        size = DpSize(1000.dp, 700.dp)
    )

    val appIcon = painterResource("logo.png")

    val trayState = rememberTrayState()
    Tray(
        state = trayState,
        icon = appIcon,
        tooltip = "Octahedron",
    )

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Octahedron",
        icon = appIcon,
        resizable = true
    ) {
        MaterialTheme(colorScheme = darkColorScheme()) {
            Surface(Modifier.fillMaxSize()) {
                App()
            }
        }
    }
}
