package bista.shiddarth.photopulse.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import bista.shiddarth.photopulse.R
import bista.shiddarth.photopulse.model.Post

class PostViewModel : ViewModel() {
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts

    init {
        loadDefaultPosts()
    }

    private fun loadDefaultPosts() {
        _posts.addAll(
            listOf(
                Post(
                    1,
                    R.drawable.city,
                    null,
                    R.drawable.avatar1,
                    "Shiddarth",
                    "Bista",
                    "2025/01/23",
                    "City of Dreams",
                    listOf("buildings")
                ),
                Post(
                    2,
                    R.drawable.girlpar,
                    null,
                    R.drawable.avatar2,
                    "Selena",
                    "Gomez",
                    "2024/11/22",
                    "Girl ",
                    listOf("girldinner")
                ),
                Post(
                    3,
                    R.drawable.headphones,
                    null,
                    null,
                    "Ramu",
                    "Kaka",
                    "2023/08/10",
                    "Headphones",
                    listOf("music", "beats", "drake", "almonds")
                ),
                Post(
                    4, R.drawable.flowers, null,
                    null, "Xiaoyou", "Shao", "2025/01/03", "Flower garden"
                ),
                Post(
                    5,
                    R.drawable.moon,
                    null,
                    R.drawable.avatar1,
                    "Neal",
                    "Armstrong",
                    "2025/01/13",
                    "One small step for man , big step for manking",
                    listOf("moon")
                )
            )
        )
    }

    fun addPostFromUri(uri: Uri) {
        val newPost = Post(
            imageId = (100..200).random(),
            imageResourceId = 0,
            imageUri = uri,
            profilePicId = null,
            firstName = "Shiddarth",
            lastName = "Bista",
            uploadedAt = "Just now",
            description = "New photo",
            hashtags = listOf("new")
        )
        _posts.add(0, newPost)
    }

}