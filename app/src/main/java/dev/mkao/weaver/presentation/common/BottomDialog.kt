package dev.mkao.weaver.presentation.common
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.presentation.bookmarks.BookmarkViewModel
import dev.mkao.weaver.presentation.details.NewsArticleUi


@Composable
fun BottomDialog(
	article: Article,
	bookmarkViewModel: BookmarkViewModel,
	onReadFullStoryButtonClicked: () -> Unit
) {
	var showNewsArticleUi by remember { mutableStateOf(false) }

	if (showNewsArticleUi) {
		NewsArticleUi(
			article = article,
			bookmarkViewModel = bookmarkViewModel,
			onBackPressed = { showNewsArticleUi = false }
		)
	} else {
		Column(modifier = Modifier.fillMaxWidth()) {
			Box(modifier = Modifier
				.fillMaxWidth()
				.height(340.dp)) {
				LoadingImage(
					imageUrl = article.image,
					modifier = Modifier
						.fillMaxSize()
						.scale(1.5f)
						.offset(y = (-18).dp)
						.scale(1.2f)
				)
				Text(
					text = article.title,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					color = Color.White,
					modifier = Modifier
						.align(Alignment.BottomStart)
						.padding(16.dp)
				)
			}
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.background(MaterialTheme.colorScheme.surface)
					.padding(16.dp)
			) {
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = article.content?.take(550) ?: "No content available",
					maxLines = 5,
					textAlign = TextAlign.Justify,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodyMedium
				)
				Spacer(modifier = Modifier.height(8.dp))
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
//					Text(
//						text = article.author ?: "",
//						style = MaterialTheme.typography.bodySmall,
//						fontWeight = FontWeight.Bold
//					)
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