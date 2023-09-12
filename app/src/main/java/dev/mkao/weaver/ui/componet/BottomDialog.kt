
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.ui.componet.LoadingImage


@Composable
fun BottomDialog(
	article: Article,
	onReadFullStoryButtonClicked: () -> Unit
) {
	Surface(
		modifier = Modifier.padding(16.dp)
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(
				text = article.title,
				style = MaterialTheme.typography.titleMedium
			)
			Spacer(modifier = Modifier.height(8.dp))
			Text(
				text = article.description ?: "",
				style = MaterialTheme.typography.bodyMedium
			)
			Spacer(modifier = Modifier.height(8.dp))
			LoadingImage(imageUrl = article.urlToImage)
			Spacer(modifier = Modifier.height(8.dp))
			Text(
				text = article.content,
				style = MaterialTheme.typography.bodyMedium
			)
			Spacer(modifier = Modifier.height(8.dp))
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = article.author ?: "",
					style = MaterialTheme.typography.bodySmall,
					fontWeight = FontWeight.Bold
				)
				Text(
					text = article.source.name ?: "",
					style = MaterialTheme.typography.bodySmall,
					fontWeight = FontWeight.Bold
				)
			}
			Spacer(modifier = Modifier.height(8.dp))
			Button(
				modifier = Modifier.fillMaxWidth(),
				onClick = onReadFullStoryButtonClicked
			) {
				Text(text = "Read Full Story")
			}
		}
	}
}


