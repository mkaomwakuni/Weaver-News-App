package dev.mkao.weaver.presentation.common
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.presentation.details.NewsArticleUi
import dev.mkao.weaver.viewModels.SharedViewModel


@Composable
fun BottomDialog(
	article: Article,
	onReadFullStoryButtonClicked: () -> Unit
) {

	var showNewsArticleUi by remember { mutableStateOf(false) }
	val sharedViewModel: SharedViewModel = hiltViewModel()
	val selectedCategory by sharedViewModel.selectedCategory.collectAsState()

	if (showNewsArticleUi) {
		NewsArticleUi (
			article = article,
			onBackPressed = { showNewsArticleUi = false }
		)
	} else {
		Surface(
			modifier = Modifier
				.fillMaxWidth(),
			color = MaterialTheme.colorScheme.surface
		) {
			Column(
				modifier = Modifier
					.padding(10.dp)
					.background(color = MaterialTheme.colorScheme.surface)
					.fillMaxWidth()
					.padding(bottom = 20.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Text(
					text = article.title,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold
				)
				Spacer(modifier = Modifier.height(8.dp))
				LoadingImage(imageUrl = article.urlToImage)
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = article.content?.take(550) ?: "none",
					maxLines = 5,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodyMedium
				)
				Spacer(modifier = Modifier.height(8.dp))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Text(
						text = article.author ?: "Source",
						style = MaterialTheme.typography.bodySmall,
						fontWeight = FontWeight.Bold
					)
					Text(
						text = article.source.name ?: "Google News",
						style = MaterialTheme.typography.bodySmall,
						fontWeight = FontWeight.Bold
					)
				}
				Spacer(modifier = Modifier.height(8.dp))
				Button(
					modifier = Modifier.fillMaxWidth(),
					onClick = {
						onReadFullStoryButtonClicked()
						showNewsArticleUi = true
					}
				) {
					Text(text = "Read Full Story")
				}
			}
		}
	}
}


//@Preview
//@Composable
//fun BottomDialogPreview() {
//	val sampleArticle = Article(
//		// Initialize the sample data for testing
//		// Make sure to replace these values with actual data
//		source = Source(name = "Sample Source", id = "bbc", category = "general", url = ""),
//		author = "John Doe",
//		title = "Sample Article Title",
//		description = "Sample article content goes here.",
//		urlToImage = "https://example.com/sample_image.jpg",
//		publishedAt = "2024-01-29T12:34:56Z",
//		content = "Sample article content goes here.",
//		url = "https://example.com/sample_image.jpg"
//	)
//
//	Surface {
//		BottomDialog(
//			article = sampleArticle,
//			onReadFullStoryButtonClicked = { /* Handle click event if needed */ }
//		)
//	}
//}

