package dev.mkao.weaver.ui.article

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Source

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticleUi(
	url: String?,
	onBackPressed: () -> Unit
) {
	val context = LocalContext.current
	var isLoading by remember {
		mutableStateOf(true)
	}

	// Sample article data, replace it with your actual data


	Scaffold(
		modifier = Modifier.fillMaxSize(),
		topBar = {
			TopAppBar(
				title = { Text(text = "Article", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) },
				actions = {
					IconButton(onClick = { /* Handle favorites action */ }) {
						Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite")
					}
					IconButton(onClick = onBackPressed) {
						Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = MaterialTheme.colorScheme.primaryContainer,
					titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
					actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
				)
			)
		}
	) { paddingValues ->
		LazyColumn(
			contentPadding = paddingValues,
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			item {
				// Article image with fading effect
				FadingArticleImage(url = url, isLoading = isLoading)
			}
			item {
				// Article title
				Text(
					text = article.title,
					fontWeight = FontWeight.Bold,
					fontSize = 24.sp,
					modifier = Modifier.padding(horizontal = 16.dp)
				)
			}
			item {
				// Source, category, and date
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 16.dp),
					horizontalArrangement = Arrangement.spacedBy(8.dp)
				) {
					Text(text = article.source.name ?: "Unknown Source", color = Color.Gray)
					Text(text = article.publishedAt ?: "Unknown Date", color = Color.Gray)
				}
			}
			item {
				// Article content
				Text(
					text = article.content ?: "No content available",
					fontSize = 16.sp,
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 16.dp)
				)
			}
		}
	}
}
@Composable
fun FadingArticleImage(url: String?, isLoading: Boolean) {
	var offset by remember { mutableStateOf(0f) }

	// Use CoilImage or any other image loading library
	Image(
		painter = rememberAsyncImagePainter(model = url),
		contentDescription = null,
		modifier = Modifier
			.fillMaxWidth()
			.height(300.dp)
			.background(MaterialTheme.colorScheme.primaryContainer)
			.graphicsLayer(alpha = 1 - offset.coerceIn(0f, 1f)),
		contentScale = ContentScale.Crop,
		// Add shape to round the bottom corners
		shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
	)

	if (isLoading && url != null) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(MaterialTheme.colorScheme.primaryContainer)
				.alpha(1 - offset.coerceIn(0f, 1f))
				.padding(16.dp),
			contentAlignment = Alignment.Center
		) {
			CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
		}
	}
}
@Preview(showBackground = true)
@Composable
fun NewsArticleUiPreview() {
	val article = Article(
		title = "Sample Title",
		source = Source(name = "Sample Source", id = "bbc"),
		publishedAt = "2024-02-01",
		content = "Sample content goes here.",
		url = ("https://your-image-url.com"),
		urlToImage = ("https://your-image-url.com"),
		description = "Hello",
		author = "tyuu"?:"adan")

	NewsArticleUi(
		article = article,
		url = "https://your-image-url.com",
		onBackPressed = { /* Handle back press */ }
	)
}
