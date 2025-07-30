package bista.shiddarth.photopulse.filters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ColorSpace
import android.graphics.ImageDecoder
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.graphics.scale
import bista.shiddarth.photopulse.ui.theme.fancyFont
import bista.shiddarth.photopulse.ui.theme.shadowsFontFamily
import jp.co.cyberagent.android.gpuimage.GPUImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GpuFilterScreen(
    uri: Uri,
    onConfirm: (Uri, String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf(GpuPhotoFilter.NONE) }
    var originalBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var filteredBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val filters = GpuPhotoFilter.entries.toTypedArray()
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    val filterBitmapCache = remember { mutableMapOf<GpuPhotoFilter, Bitmap>() }
    val previewBitmaps = remember { mutableStateMapOf<GpuPhotoFilter, Bitmap>() }
    var savedFilteredUri by remember { mutableStateOf<Uri?>(null) }
    val gpuImage = remember { GPUImage(context) }
    var caption by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    suspend fun Uri.toBitmap(context: Context): Bitmap = withContext(Dispatchers.IO) {
        val source = ImageDecoder.createSource(context.contentResolver, this@toBitmap)
        ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
            decoder.setTargetColorSpace(ColorSpace.get(ColorSpace.Named.SRGB))
            decoder.setTargetSize(1080, 1080) // optional
        }
    }

    LaunchedEffect(uri) {
        originalBitmap = uri.toBitmap(context)
    }

    LaunchedEffect(originalBitmap) {
        withContext(Dispatchers.IO) {
            originalBitmap?.let { bitmap ->
                filters.forEach { filter ->
                    gpuImage.setImage(bitmap)
                    gpuImage.setFilter(filter.filter)
                    val preview = gpuImage.bitmapWithFilterApplied
                    previewBitmaps[filter] = preview.scale(100, 100)
                }
            }
        }
    }

    fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val fileName = "image_${System.currentTimeMillis()}.png"
        val file = File(cachePath, fileName)
        return try {
            FileOutputStream(file).use { outStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            }
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    LaunchedEffect(selectedFilter, originalBitmap) {
        withContext(Dispatchers.IO) {
            originalBitmap?.let { bitmap ->
                val cached = filterBitmapCache[selectedFilter]
                if (cached != null) {
                    filteredBitmap = cached
                } else {
                    val filtered = when (selectedFilter) {
                        GpuPhotoFilter.NONE -> bitmap // Skip GPUImage completely
                        else -> {
                            gpuImage.setImage(bitmap)
                            gpuImage.setFilter(selectedFilter.filter)
                            gpuImage.bitmapWithFilterApplied
                        }
                    }

                    filteredBitmap = filtered
                    filterBitmapCache[selectedFilter] = filtered
                    savedFilteredUri = bitmapToUri(context, filtered)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Apply Filter", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFF121212)),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        filteredBitmap?.let { bitmap ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val savedUri = bitmapToUri(context, bitmap)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Photo uploaded", Toast.LENGTH_SHORT)
                                        .show()
                                    onConfirm(savedUri ?: uri, caption)
                                }
                            }
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Apply", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF121212))
        ) {
            val displayBitmap = filteredBitmap ?: originalBitmap

            Crossfade(
                targetState = displayBitmap,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            ) { image ->
                image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Filtered Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            OutlinedTextField(
                value = caption,
                onValueChange = { caption = it },
                label = { Text("Add a caption...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                maxLines = 1,
                colors = OutlinedTextFieldDefaults. colors(Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                )
            )


            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(filters) { index, filter ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val previewBitmap = previewBitmaps[filter]

                        Text(
                            text = filter.label,
                            fontFamily = fancyFont,
                            fontSize = 14.sp,
                            color = if (filter == selectedFilter) Color.Cyan else Color.White
                        )

                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (filter == selectedFilter) Color.Cyan.copy(alpha = 0.3f)
                                    else Color.Gray.copy(alpha = 0.2f)
                                )
                                .clickable {
                                    selectedFilter = filter
                                    selectedFilterIndex = index
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (previewBitmap != null) {
                                Image(
                                    bitmap = previewBitmap.asImageBitmap(),
                                    contentDescription = filter.label,
                                    modifier = Modifier.size(100.dp)
                                )
                            } else {
                                Text(
                                    text = filter.label.take(2),
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}