package com.Found404.paypro.ui.components


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Found404.paypro.AuthDependencyProvider
import com.Found404.paypro.R
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayProNavigationDrawer(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current
    val authService = AuthDependencyProvider.getInstance().getAuthService()

    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItem(
            title = "Logout",
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_logout),
            unselectedIcon = ImageVector.vectorResource(R.drawable.ic_logout),
            onClick = {
                authService.logoutUser(context)

                navController.navigate("welcome") {
                    popUpTo("addingMerchants") {
                        inclusive = true
                    }
                }
            }
        )
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val customFontFamily = FontFamily(
        Font(R.font.montserrat_bold, FontWeight.Bold),
    )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader(context)

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                navigationItems.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = customFontFamily,
                                modifier = Modifier.padding(start = 20.dp)
                            )
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()

                                item.onClick?.invoke() ?: item.route?.let {
                                    navController.navigate(it)
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        // Content outside the drawer
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        PayProTitle(
                            text = stringResource(id = R.string.app_name),
                            modifier = Modifier.padding(10.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Navigation menu",
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                )
            },
            content = { innerPadding ->
                // Invoke the lambda to provide the main content
                content(innerPadding)
            }
        )
    }
}


@Composable
fun DrawerHeader(context: Context) {
    val authService = AuthDependencyProvider.getInstance().getAuthService()
    val user = authService.getLoggedInUser(context)

    val customFontFamily = FontFamily(
        Font(R.font.montserrat_bold, FontWeight.Bold),
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_user),
            contentDescription = "User Image",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        PayProTitle(
            text = "${user.firstName} ${user.lastName}",
            fontSize = 20.sp,
        )

        user.email?.let {
            Text(
                text = it,
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = customFontFamily
            )
        }
    }
}



data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route: String? = "welcome",
    val onClick: (() -> Unit)? = null
)