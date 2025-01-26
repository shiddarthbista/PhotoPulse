package bista.shiddarth.photopulse

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import bista.shiddarth.photopulse.model.BottomNavItem
import bista.shiddarth.photopulse.screens.HomeScreen
import com.rahad.riobottomnavigation.composables.RioBottomNavItemData
import com.rahad.riobottomnavigation.composables.RioBottomNavigation

@Composable
fun PhotoPulseApp() {
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Explore,
        BottomNavItem.Profile,
        BottomNavItem.Notifications,
    )

    val buttons = bottomNavItems.mapIndexed { index, bottomNavItem ->
        RioBottomNavItemData(
            imageVector = bottomNavItem.icon,
            selected = index == selectedIndex.intValue,
            onClick = { selectedIndex.intValue = index },
            label = bottomNavItem.label
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(buttons = buttons)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Handle the screen content based on the selected index
        ScreenContent(
            selectedIndex.intValue, modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Composable
fun ScreenContent(selectedIndex: Int, modifier: Modifier = Modifier) {
    when (selectedIndex) {
        0 -> HomeScreen()
        1 -> Text(text = "asd")
        2 -> Text(text = "profile")
        3 -> Text("asd")
    }
}

@Composable
fun BottomNavigationBar(buttons: List<RioBottomNavItemData>) {

    RioBottomNavigation(
        backgroundColor = Color.Black,
        cardTopCornerSize = 0.dp,
        fabIcon = ImageVector.vectorResource(id = R.drawable.baseline_cameraswitch_24),
        buttons = buttons,
        fabSize = 75.dp,
        barHeight = 85.dp,
        selectedItemColor = Color.White,
        fabBackgroundColor = colorResource(id = R.color.fab_color),
    )
}