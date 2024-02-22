//package dev.mkao.weaver.presentation.home
//
//
//import android.annotation.SuppressLint
//import androidx.compose.animation.core.animateIntAsState
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.outlined.Menu
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import dev.mkao.weaver.R
//import dev.mkao.weaver.domain.model.Article
//import dev.mkao.weaver.domain.model.Source
//
//@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun TopSection(
//article: Article
//) {
//    var selectedImageIndex by remember { mutableIntStateOf(0) }
//    val categories = listOf("General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment")
//    Scaffold(
//        topBar = {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 10.dp)
//                                .statusBarsPadding()
//                        ) {
//                            Text(
//                            modifier = Modifier.fillMaxWidth(),
//                            text = "WEAVER 𝐍𝐄𝐖𝐒",
//                            fontSize = 21.sp,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center
//                        )
//                            Spacer(modifier = Modifier.height(20.dp))
//                            TopAppBar(
//                                modifier = Modifier
//                                    .padding(10.dp)
//                                    .height(60.dp)
//                                    .border(
//                                        width = 0.dp,
//                                        color = Color.Transparent,
//                                        shape = RoundedCornerShape(12.dp)
//                                    )
//                                    .clip(shape = RoundedCornerShape(12.dp)),
//                                title = { /* Optional title content */ },
//
//                                navigationIcon = {
//                                    IconButton(
//                                        onClick = { /* Handle menu icon click */ }
//                                    ) {
//                                        Icon(
//                                            imageVector = Icons.Outlined.Menu,
//                                            contentDescription = "Menu"
//                                        )
//                                    }
//                                },
//                                actions = {
//                                    IconButton(
//                                        onClick = { /* Handle UI Componet icon click */ }
//                                    ) {
//                                        Icon(
//                                            imageVector = Icons.Filled.Search,
//                                            contentDescription = "Search"
//                                        )
//                                    }
//                                    IconButton(
//                                        onClick = { /* Handle notification icon click */ }
//                                    ) {
//                                        Icon(
//                                            imageVector = Icons.Filled.Notifications,
//                                            contentDescription = "Notifications"
//                                        )
//                                    }
//                                }
//                            )
//                            Spacer(modifier = Modifier.height(15.dp))
//                            Row(
//                                modifier = Modifier
//                                    .padding(start = 5.dp, end = 5.dp)
//                                    .fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween,
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                // Add Breaking News headline below TopAppBar
//                                Text(
//                                    text = "Breaking News",
//                                    fontSize = 24.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    modifier = Modifier.padding(start = 16.dp)
//                                )
//                                TextButton(
//                                    onClick = { /*TODO*/ },
//                                    modifier = Modifier.padding(end = 5.dp)
//                                ) {
//                                    Text(
//                                        text = "View All",
//                                        color = colorResource(id = R.color.body)
//                                    )
//                                }
//                            }
//                            Spacer(modifier = Modifier.height(10.dp))
//
//                            // Add more image resources here...
//
//
//                            var previousSelectedImageIndex by remember { mutableStateOf(0) }
//                            val animateSelectedImageIndex by animateIntAsState(targetValue = selectedImageIndex)
//
//                            LazyColumn {
//                                item {
//                                    LazyRow(
//                                        contentPadding = PaddingValues(horizontal = 10.dp),
//                                        horizontalArrangement = Arrangement.SpaceBetween
//                                    ) {
//                                        items(categories.size) { index ->
//                                            val imageResource = categories[index]
//                                            val isSelected = index == animateSelectedImageIndex
//
//                                            Box(
//                                                modifier = Modifier
//                                                    .padding(4.dp)
//                                                    .clip(RoundedCornerShape(18.dp))
//                                                    .height(if (isSelected) 210.dp else 150.dp)
//                                                    .width(if (isSelected) 350.dp else 250.dp)
//                                            ) {
//                                                AsyncImage(
//                                                    modifier = Modifier
//                                                        .height(140.dp) // Adjust the height as needed
//                                                        .width(120.dp),
//                                                    model = ImageRequest.Builder(LocalContext.current)
//                                                        .data(article.urlToImage)
//                                                        .build(),
//                                                    contentDescription = null,
//                                                    contentScale = ContentScale.Crop
//                                                )
//
//                                                if (isSelected) {
//                                                    Text(
//                                                        text = "",
//                                                        color = Color.White,
//                                                        fontSize = 20.sp,
//                                                        modifier = Modifier
//                                                            .align(Alignment.Center)
//                                                            .padding(16.dp)
//                                                    )
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                                item {
//                                    Spacer(modifier = Modifier.height(5.dp))
//                                    Row(
//                                        modifier = Modifier
//                                            .padding(start = 5.dp, end = 5.dp)
//                                            .fillMaxWidth(),
//                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        // Add Breaking News headline below TopAppBar
//                                        Text(
//                                            text = "Recommendation",
//                                            fontSize = 24.sp,
//                                            fontWeight = FontWeight.Bold,
//                                            modifier = Modifier.padding(start = 16.dp)
//                                        )
//                                        TextButton(
//                                            onClick = { /*TODO*/ },
//                                            modifier = Modifier.padding(end = 5.dp)
//                                        ) {
//                                            Text(
//                                                text = "View All",
//                                                color = Color.Red
//                                            )
//                                        }
//                                    }
//                                    Spacer(modifier = Modifier.height(10.dp))
//                                }
//                            }
//                        }
//                    },
//                    content = { paddingValues ->
//                        val modifier = Modifier.padding(paddingValues)
//                        LazyColumn(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(horizontal = 16.dp)
//                        ) {
//
//                            // Add RecyclerView here for the news articles
//                                item {
//                                    LazyRow(
//                                        contentPadding = PaddingValues(horizontal = 10.dp),
//                                        horizontalArrangement = Arrangement.SpaceBetween
//                                    ) {
//                                        items(categories.size) { index ->
//
//                                            Box(
//                                                modifier = Modifier
//                                                    .padding(4.dp)
//                                                    .clip(RoundedCornerShape(18.dp))
//                                            ) {
//                                                AsyncImage(
//                                                    modifier = Modifier
//                                                        .height(140.dp) // Adjust the height as needed
//                                                        .width(120.dp),
//                                                    model = ImageRequest.Builder(LocalContext.current)
//                                                        .data(article.urlToImage)
//                                                        .build(),
//                                                    contentDescription = null,
//                                                    contentScale = ContentScale.Crop
//                                                )
//
//                                            }
//                                        }
//                                    }
//                                }
//
//                        }
//                    }
//                )
//            }
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//@Preview
//fun TopSectionPreview() {
//    val dummyArticle = Article(
//        source = Source(id = "sourceId", name = "News Source"),
//        author = "John Doe",
//        title = "Sample Title",
//        content = "This is the content of the article. It can be a longer text describing the article.",
//        description = "A short description of the article.",
//        url = "https://www.example.com/sample-article",
//        urlToImage = "https://www.example.com/sample-image.jpg",
//        publishedAt = "2024-01-3"
//    )
//    TopSection(article = dummyArticle)
//}
