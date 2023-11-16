package tech.annexflow.sample.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.rememberNavigator
import tech.annexflow.precompose.navigation.typesafe.ExperimentalTypeSafeApi
import tech.annexflow.precompose.navigation.typesafe.PopUpTo
import tech.annexflow.precompose.navigation.typesafe.dialog
import tech.annexflow.precompose.navigation.typesafe.floating
import tech.annexflow.precompose.navigation.typesafe.generateRoutePattern
import tech.annexflow.precompose.navigation.typesafe.group
import tech.annexflow.precompose.navigation.typesafe.navigate
import tech.annexflow.precompose.navigation.typesafe.scene
import tech.annexflow.precompose.navigation.typesafe.typesafeRoute

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTypeSafeApi::class)
@Composable
internal fun AppNavigation() {
    val navigator = rememberNavigator()
    val currentEntry by navigator.currentEntry.collectAsStateWithLifecycle(null)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = currentEntry?.typesafeRoute.toString())
                }
            )
        }
    ) { innerPadding ->
        /**
         * or use custom TypesafeNavHost with
         * initialRoute = AppRoutes.Home
         **/
        NavHost(
            initialRoute = AppRoutes.Home.generateRoutePattern(),
            navigator = navigator,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            scene<AppRoutes.Home> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Button(onClick = {
                        navigator.navigate(route = AppRoutes.Scene(id = 123))
                    }) {
                        Text("Go to scene #123")
                    }
                    Button(onClick = {
                        navigator.navigate(route = AppRoutes.Scene(id = null))
                    }) {
                        Text("Go to scene null")
                    }
                    Button(onClick = {
                        navigator.navigate(route = AppRoutes.Dialog("Dialog text"))
                    }) {
                        Text("Go to dialog")
                    }
                    var isSwitchChecked by rememberSaveable { mutableStateOf(false) }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = {
                            navigator.navigate(AppRoutes.Floating(isSwitchChecked))
                        }) {
                            Text("Go to floating")
                        }
                        Switch(
                            checked = isSwitchChecked,
                            onCheckedChange = {
                                isSwitchChecked = it
                            }
                        )
                    }
                    Button(
                        onClick = {
                            navigator.navigate(route = generateRoutePattern<AppRoutes.Group>())
                        }
                    ) {
                        Text("Go to group")
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
            dialog<AppRoutes.Dialog> {
                AlertDialog(
                    onDismissRequest = navigator::goBack
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Text(text = text)
                        Button(
                            onClick = navigator::goBack
                        ) {
                            Text("Back")
                        }
                    }
                }
            }
            floating<AppRoutes.Floating> {
                ModalBottomSheet(
                    onDismissRequest = navigator::goBack
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = if (isCenter) Alignment.CenterHorizontally else Alignment.Start
                    ) {
                        Text(text = "Floating!")
                        Button(
                            onClick = navigator::goBack
                        ) {
                            Text("Back")
                        }
                    }
                }
            }
            group<AppRoutes.Group>(
                initialRoute = AppRoutes.Group.First.generateRoutePattern(),
            ) {
                scene<AppRoutes.Group.First> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "GroupRoute: $id")
                        Button(
                            onClick = {
                                navigator.navigate(route = AppRoutes.Group.Second)
                            }
                        ) {
                            Text(text = "Next")
                        }
                        Button(
                            onClick = navigator::goBack
                        ) {
                            Text(text = "Home")
                        }
                    }
                }
                scene<AppRoutes.Group.Second> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "GroupRoute: $id")
                        Button(
                            onClick = navigator::goBack
                        ) {
                            Text(text = "Previous")
                        }
                        Button(
                            onClick = {
                                navigator.navigate(
                                    route = AppRoutes.Home,
                                    options = NavOptions(
                                        popUpTo = PopUpTo(route = AppRoutes.Home),
                                        launchSingleTop = true
                                    )
                                )
                            }
                        ) {
                            Text(text = "Home")
                        }
                    }
                }
            }
        }
    }
}