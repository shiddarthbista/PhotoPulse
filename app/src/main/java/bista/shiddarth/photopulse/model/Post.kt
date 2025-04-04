package bista.shiddarth.photopulse.model

import android.net.Uri

data class Post(
    val imageId:Int,
    val imageResourceId: Int,
    val imageUri: Uri? = null,
    val profilePicId: Int?,
    val firstName: String,
    val lastName: String,
    val uploadedAt: String,
    val description: String,
    val hashtags: List<String> = emptyList()
)
