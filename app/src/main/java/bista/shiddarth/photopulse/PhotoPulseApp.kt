package bista.shiddarth.photopulse

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import bista.shiddarth.photopulse.model.BottomNavItem
import bista.shiddarth.photopulse.screens.ExploreScreen
import bista.shiddarth.photopulse.screens.HomeScreen
import bista.shiddarth.photopulse.screens.NotificationScreen
import bista.shiddarth.photopulse.screens.ProfileScreen
import bista.shiddarth.photopulse.ui.theme.fancyFont
import bista.shiddarth.photopulse.viewmodel.PostViewModel
import com.rahad.riobottomnavigation.composables.RioBottomNavItemData
import com.rahad.riobottomnavigation.composables.RioBottomNavigation
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat

@Composable
fun PhotoPulseApp() {
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val postViewModel: PostViewModel = viewModel()


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
            BottomNavigationBar(buttons = buttons, postViewModel = postViewModel)
        },
        modifier = Modifier.fillMaxSize()
    ) { _ ->
        ScreenContent(
            selectedIndex.intValue,
            postViewModel
        )
    }
}

@Composable
fun ScreenContent(selectedIndex: Int,postViewModel: PostViewModel) {
    when (selectedIndex) {
        0 -> HomeScreen(postViewModel)
        1 -> ExploreScreen()
        2 -> ProfileScreen()
        3 -> NotificationScreen()
    }
}

@Composable
fun BottomNavigationBar(buttons: List<RioBottomNavItemData>, postViewModel: PostViewModel) {
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
                imageUri?.let {
                    postViewModel.addPostFromUri(it)
                    Toast.makeText(context, "Photo uploaded 📷", Toast.LENGTH_SHORT).show()
                }
                showDialog = false
            }
        }
    )

    fun launchCamera() {
        val timeStamp = SimpleDateFormat.getTimeInstance()
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
                    imageUri?.let {
                        postViewModel.addPostFromUri(it)
                        Toast.makeText(context, "Photo uploaded 📷", Toast.LENGTH_SHORT).show()
                    }
                    showDialog = false
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
        UploadOptionsSheet(
            onDismiss = { showDialog = false },
            onCameraClick = {
                val permission = Manifest.permission.CAMERA
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    launchCamera()
                    showDialog = false
                } else {
                    requestPermissionLauncher.launch(permission)
                }
            },
            onGalleryClick = {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                galleryLauncher.launch(galleryIntent)
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadOptionsSheet(
    onDismiss: () -> Unit,
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = Color.Black,
        tonalElevation = 4.dp,
        scrimColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.32f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 20.dp)
                .windowInsetsPadding(BottomSheetDefaults.windowInsets),
            horizontalAlignment = Alignment.Start
        ) {
            SheetOptionRow(
                icon = Icons.Default.PhotoCamera,
                label = "Take a Photo",
                onClick = {
                    onCameraClick()
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        onDismiss()
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            SheetOptionRow(
                icon = Icons.Default.Image,
                label = "Choose from Gallery",
                onClick = {
                    onGalleryClick()
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        onDismiss()
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun SheetOptionRow(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White
            ),
            fontFamily = fancyFont,
            fontSize = 20.sp
        )
    }
}
