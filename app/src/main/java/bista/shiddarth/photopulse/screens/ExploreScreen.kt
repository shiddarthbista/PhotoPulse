package bista.shiddarth.photopulse.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bista.shiddarth.photopulse.R
import bista.shiddarth.photopulse.composables.ImageItem
import bista.shiddarth.photopulse.composables.RowImageItem
import bista.shiddarth.photopulse.ui.theme.displayFontFamily
import bista.shiddarth.photopulse.ui.theme.fancyFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen() {
    val imageList = listOf(
        R.drawable.city, R.drawable.flowers, R.drawable.moon,
        R.drawable.date, R.drawable.house, R.drawable.plant,
        R.drawable.mountain, R.drawable.palace, R.drawable.peacock
    )

    val waterImageList = listOf(R.drawable.water2,R.drawable.water4,R.drawable.water3,R.drawable.water2,R.drawable.water5)
    val sportsImageList = listOf(R.drawable.sports1,R.drawable.sports2,R.drawable.sports3,R.drawable.sports4,R.drawable.sports5)
    val blackAndWhiteImageList = listOf(R.drawable.date,R.drawable.house,R.drawable.mountain,R.drawable.plant,R.drawable.clock)

    var searchText by remember { mutableStateOf("Search") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Explore",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 20.dp, end = 10.dp)
        ) {
//            item {
//                SearchBarComposable(
//                    query = searchText,
//                    onQueryChange = { },
//                    onSearch = { /* Handle search logic */ }
//                )
//            }
            item { SearchBar1() }
            item { JustForYouSection(imageList) }
            item { ImageRow(title = "Water", "Hydrate your soul", images = waterImageList) }
            item { ImageRow(title = "Sports", "Talent,Determination,Grit", images = sportsImageList) }
            item { ImageRow(title = "Black & White", "Colour Drained", images = blackAndWhiteImageList) }
            item { ImageRow(title = "Water", "Hydrate your soul", images = waterImageList) }
            item { ImageRow(title = "Sports", "Talent,Determination,Grit", images = sportsImageList) }
            item { ImageRow(title = "Black & White", "Colour Drained", images = blackAndWhiteImageList) }
            item { ImageRow(title = "Water", "Hydrate your soul", images = waterImageList) }
            item { ImageRow(title = "Sports", "Talent,Determination,Grit", images = sportsImageList) }
            item { ImageRow(title = "Black & White", "Colour Drained", images = blackAndWhiteImageList) }
            item { Spacer(modifier = Modifier.height(90.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComposable(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    var active by remember { mutableStateOf(false) }
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {
            onSearch(it)
            active = false
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(text = "Search", color = Color.White) },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "search",
                tint = Color.White
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color.Black,
            inputFieldColors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Red
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

    }
}

@Composable
fun JustForYouSection(images: List<Int>) {
    Text(
        "Just For You",
        fontSize = 20.sp,
        modifier = Modifier.padding(top = 20.dp, bottom = 5.dp),
        color = Color.White,
        fontFamily = displayFontFamily
    )
    Text(
        "Keeping you inspired",
        fontFamily = fancyFont,
        fontSize = 12.sp,
        color = Color.Gray
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.height(550.dp)
    ) {
        items(images.size) { index ->
            ImageItem(images[index])
        }
    }
}

@Composable
fun SearchBar1() {
    var query by remember { mutableStateOf("Search") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black)
            .border(2.dp, Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = { query = it },
            textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { /* Handle search */ })
        )
    }
}


@Composable
fun ImageRow(title: String, subtitle: String, images: List<Int>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            title,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp, bottom = 5.dp),
            color = Color.White,
            fontFamily = displayFontFamily
        )
        Text(
            subtitle,
            fontFamily = fancyFont,
            fontSize = 13.sp,
            color = Color.Gray
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.height(200.dp)
        ) {
            items(images.size) { index ->
                RowImageItem(images[index])
            }
        }
    }
}