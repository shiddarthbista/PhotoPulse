package bista.shiddarth.photopulse

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import bista.shiddarth.photopulse.model.BottomNavItem
import bista.shiddarth.photopulse.screens.ExploreScreen
import bista.shiddarth.photopulse.screens.HomeScreen
import bista.shiddarth.photopulse.screens.NotificationScreen
import bista.shiddarth.photopulse.screens.ProfileScreen
import com.rahad.riobottomnavigation.composables.RioBottomNavItemData
import com.rahad.riobottomnavigation.composables.RioBottomNavigation
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

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
        ScreenContent(
            selectedIndex.intValue
        )
    }
}

@Composable
fun ScreenContent(selectedIndex: Int) {
    when (selectedIndex) {
        0 -> HomeScreen()
        1 -> ExploreScreen()
        2 -> ProfileScreen()
        3 -> NotificationScreen()
    }
}

@Composable
fun BottomNavigationBar(buttons: List<RioBottomNavItemData>) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var cameraUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                imageUri = cameraUri
                // Handle the taken picture (e.g., display it, save it, etc.)
                // You can use the 'imageUri' here
            }
        }
    )

    fun launchCamera() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            context.cacheDir
        )
        val localCameraUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        cameraUri = localCameraUri
        cameraLauncher.launch(localCameraUri)
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                if (selectedImageUri != null) {
                    imageUri = selectedImageUri
                    // Handle the selected image from the gallery
                    // You can use the 'imageUri' here
                }
            }
        }
    )
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launchCamera()
                // Permission is granted. Continue the action or workflow in your app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the same time,
                // respect the user's decision. Don't link to system settings in an effort
                // to convince the user to change their decision.
            }
        }

    RioBottomNavigation(
        backgroundColor = Color.Black,
        cardTopCornerSize = 0.dp,
        fabIcon = ImageVector.vectorResource(id = R.drawable.baseline_cameraswitch_24),
        buttons = buttons,
        fabSize = 75.dp,
        barHeight = 85.dp,
        selectedItemColor = Color.White,
        fabBackgroundColor = colorResource(id = R.color.fab_color),
        onFabClick = { showDialog = true }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Choose an action") },
            text = {
                Column {
                    TextButton(onClick = {
                        val permission = Manifest.permission.CAMERA

                        if (ContextCompat.checkSelfPermission(
                                context,
                                permission
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                            val file = File.createTempFile(
                                "JPEG_${timeStamp}_",
                                ".jpg",
                                context.cacheDir
                            )
                            cameraUri = FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.provider",
                                file
                            )
                            launchCamera()
                            showDialog = false
                        } else {
                            requestPermissionLauncher.launch(permission)

                        }
                    }) {
                        Text("Take Picture")
                    }
                    TextButton(onClick = {
                        val galleryIntent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        galleryLauncher.launch(galleryIntent)
                        showDialog = false
                    }) {
                        Text("Choose from Gallery")
                    }
                }
            },
            confirmButton = {}
        )
    }
}