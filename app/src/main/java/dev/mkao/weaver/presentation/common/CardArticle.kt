package dev.mkao.weaver.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.util.Dimens
import dev.mkao.weaver.util.articleDateFormat

@Composable
fun CardArtiCle(
    article: Article,
    onReadFullStoryClicked: () -> Unit) {

    val date = articleDateFormat(article.publishedAt)
    val chip = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth()
            .height(140.dp)
            .clickable { onReadFullStoryClicked() }
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(140.dp)
                    .width(120.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.image)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(horizontal = Dimens.ExtraSmallPadding)
                    .fillMaxWidth()

            ) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .height(25.dp)
                        .background(chip.secondary, shape = RoundedCornerShape(3.dp))
                ){
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = article.source.name?: "Google News",
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = Color.Black,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.description?: stringResource(R.string.Error404),
                    fontSize = 12.sp,
                    maxLines = 2,
                    color = Color.Gray
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = article.author?.split(",")?.firstOrNull() ?: stringResource(R.string.Contemporary_Sources),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(Dimens.ExtraExtraPadding))
                    Text(
                        text = ".",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.width(Dimens.ExtraExtraPadding))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                        modifier = Modifier.size(15.dp),
                        tint = colorResource(id = R.color.body)
                    )
                    Spacer(modifier = Modifier.width(Dimens.ExtraSmallPadding))
                    Text(
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        text = date,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black
                    )
                }
            }
        }
    }
}
//@Preview
//@Composable
//fun Ty1(){
//    val sampleArticle = Article(
//        // Initialize the sample data for testing
//
//        source = Source(name = "Sample Source", id = "bbc", category = "", url = ""),
//        author = "John Doe",
//        title = "Sample Article Title",
//        content = "Sample article content goes here.",
//        description = "Sample article content goes here.",
//        urlToImage = "https://example.com/sample_image.jpg",
//        publishedAt = "2024-01-29T12:34:56Z",
//        url = "https://example.com/sample_image.jpg"
//    )
//    CardArtiCle(
//        article = sampleArticle,
//        onReadFullStoryClicked = { /* Handle click event if needed */ }
//    )
//}