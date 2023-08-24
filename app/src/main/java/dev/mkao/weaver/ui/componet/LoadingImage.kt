package dev.mkao.weaver.ui.componet

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.mkao.weaver.R

@Composable
fun LoadingImage (
	imageUrl: String?,
	modifier: Modifier = Modifier
){
AsyncImage(model = ImageRequest
	.Builder(LocalContext.current)
	.data(imageUrl)
	.crossfade(true)
	.build(),
	contentScale = ContentScale.Crop,
	contentDescription = "Loading Image",
	modifier = Modifier
		.clip(RoundedCornerShape(4.dp))
		.fillMaxSize()
		.aspectRatio(15 / 8f),
    placeholder = painterResource(R.drawable.ic_launcher_foreground),
	error = painterResource(id = coil.base.R.drawable.abc_vector_test)
)
}