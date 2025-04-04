package bista.shiddarth.photopulse.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    data object Home : BottomNavItem("home", "Home", Icons.Outlined.Home)
    data object Explore : BottomNavItem("explore", "Explore", Icons.Outlined.Search)
    data object AddPhoto : BottomNavItem("add_photo", "Add Photo", Icons.Outlined.AddCircle)
    data object Notifications : BottomNavItem("notifications", "Notifs", Icons.Outlined.Notifications)
    data object Profile : BottomNavItem("profile", "Profile", Icons.Outlined.Person)
}