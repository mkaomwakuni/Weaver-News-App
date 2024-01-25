//package dev.mkao.weaver.ui
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import dev.mkao.weaver.domain.model.Article
//import dev.mkao.weaver.domain.model.Source
//import dev.mkao.weaver.ui.componet.dateFormat
//
//@Composable
//fun ArticleCard(
//	modifier: Modifier = Modifier,
//	article: Article,
//	onClickingCard: (Article) -> Unit){
//	val date = dateFormat(article.publishedAt)
//	val chipColor = MaterialTheme.colorScheme
//	Card(
//		modifier = Modifier
//			.padding(1.dp)
//			.fillMaxWidth()
//			.clickable { onClickingCard(article) }
//	) {
//		Column(
//			modifier = Modifier
//				.padding(5.dp)
//				.fillMaxWidth()
//		) {
//			AsyncImage(
//				modifier = Modifier
//					.height(150.dp) // Adjust the height as needed
//					.width(150.dp),
//				model = ImageRequest.Builder(LocalContext.current)
//					.data(article.urlToImage)
//					.build(),
//				contentDescription = null,
//				contentScale = ContentScale.Crop
//			)
//			Spacer(modifier = Modifier.width(1.dp))
//			Column(modifier = Modifier.weight(1f)) {
//				Text(
//					text = article.source.name?: "Google News",
//					fontSize = 12.sp,
//					color = Color.Gray,
//					fontWeight = FontWeight.Bold
//				)
//				Spacer(modifier = Modifier.height(4.dp))
//				Text(
//					text = article.title,
//					fontWeight = FontWeight.Bold,
//					fontSize = 18.sp,
//					color = Color.Black,
//				)
//				Spacer(modifier = Modifier.height(4.dp))
//				Text(
//					text = article.content,
//					fontSize = 14.sp,
//					color = Color.Gray,
//				)
//				Spacer(modifier = Modifier.height(4.dp))
//				Row(verticalAlignment = Alignment.CenterVertically) {
//					Text(
//						text = " ${article.author} .",
//						fontSize = 12.sp,
//						color = Color.Gray,
//						fontWeight = FontWeight.Bold,
//					)
//					Spacer(modifier = Modifier.width(8.dp))
//					Text(
//						text = article.publishedAt,
//						fontSize = 12.sp,
//						color = Color.Gray,
//					)
//				}
//			}
//		}
//	}
//}
//
//@Preview
//@Composable
//fun Ty(){
//	val sampleArticle = Article(
//		// Initialize the sample data for testing
//		// Make sure to replace these values with actual data
//		source = Source(name = "Sample Source", id = "bbc"),
//		author = "John Doe",
//		title = "Sample Article Title",
//		description = "Sample article content goes here.",
//		urlToImage = "https://example.com/sample_image.jpg",
//		publishedAt = "2024-01-29T12:34:56Z",
//		content = "Sample article content goes here.",
//		url = "https://example.com/sample_image.jpg"
//	)
//	ArticleCard(
//		modifier = Modifier,
//		article = sampleArticle,
//		onClickingCard = { /* Handle click event if needed */ }
//	)
//}