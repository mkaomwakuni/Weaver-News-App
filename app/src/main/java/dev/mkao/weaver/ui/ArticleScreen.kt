
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.ui.ArticleStates
import dev.mkao.weaver.ui.EventsHolder
import dev.mkao.weaver.ui.componet.DateFormat
import dev.mkao.weaver.util.Dimens
import kotlinx.coroutines.launch

@OptIn(
	ExperimentalMaterial3Api::class,
	ExperimentalPagerApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
	state: ArticleStates,
	onReadFullStoryButtonClick: (String) -> Unit,
	onEvent: (EventsHolder) -> Unit
) {
	var selectedTab by remember { mutableIntStateOf(0) }
	val coroutineScope = rememberCoroutineScope()
	val categories = listOf("General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment")
	val pagerState = rememberPagerState()
	var shouldBottomSheetShow by remember { mutableStateOf(false) }
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

	LaunchedEffect(key1 = pagerState) {
		snapshotFlow { pagerState.currentPage }.collect { page ->
			onEvent(EventsHolder.OnCategoryClicked(category = categories[page]))
		}
	}

	LaunchedEffect(key1 = Unit) {
		if (state.SearchRequest.isNotEmpty()) {
			onEvent(EventsHolder.OnSearchCategoryChanged(searchRequest = state.SearchRequest))
		}
	}

	if (shouldBottomSheetShow) {
		ModalBottomSheet(
			onDismissRequest = { shouldBottomSheetShow = false },
			sheetState = sheetState,
			content = {
				state.isSelected?.let {
					BottomDialog(
						article = it,
						onReadFullStoryButtonClicked = {
							onReadFullStoryButtonClick(it.url)
							coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
								if (!sheetState.isVisible) shouldBottomSheetShow = false
							}
						}
					)
				}
			}
		)
	}


	Scaffold(
		topBar = {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 10.dp)
					.statusBarsPadding()
			) {
				IconButton(onClick = { /*TODO*/ }) {
					Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
				}
				Spacer(modifier = Modifier.height(20.dp))

				Column(
					modifier = Modifier
						.padding(start = 16.dp)
						.fillMaxWidth(),
					verticalArrangement = Arrangement.SpaceBetween,
					horizontalAlignment = Alignment.Start
				) {
					Text(
						text = "Discover",
						color = Color.Black,
						fontSize = 28.sp,
						fontWeight = FontWeight.Bold,
					)
					Spacer(modifier = Modifier.height(5.dp))
					Text(
						text = "News from all around the world",
						color = Color.LightGray,
						fontSize = 14.sp,
						fontWeight = FontWeight.Bold,
					)
				}
			}
		},
		content = {
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(horizontal = 16.dp)
			) {
				Spacer(modifier = Modifier.height(190.dp))

				SearchBar()

				Spacer(modifier = Modifier.height(10.dp))
				LazyRow(
					contentPadding = PaddingValues(horizontal = 2.dp),
					horizontalArrangement = Arrangement.spacedBy(2.dp)
				) {
					items(categories.size) { index ->
						val category = categories[index]
						val isSelected = selectedTab == index
						TintedTextButton(
							isSelected = isSelected,
							category = category,
							onClick = { selectedTab = index }
						)
					}
				}

				Spacer(modifier = Modifier.height(20.dp))

				LazyColumn(
					modifier = Modifier
						.fillMaxWidth(),
					contentPadding = PaddingValues(2.dp),
					verticalArrangement = Arrangement.spacedBy(2.dp)
				) {
					items(state.article) { article ->
						CardArticlee(
							    article = article,
								onReadFullStoryClicked = {
								shouldBottomSheetShow = true
								onEvent(EventsHolder.OnArticleCardClicked(article))
							 }
						)
						Spacer(modifier = Modifier.height(16.dp))
					}

				}
			}
		}
	)
}

@Composable
fun CardArticlee(
	article: Article,
	onReadFullStoryClicked: () -> Unit) {
	val date = DateFormat(article.publishedAt)
	val chip = MaterialTheme.colorScheme
	Card(
		modifier = Modifier
			.padding(1.dp)
			.fillMaxWidth()
			.clickable {  onReadFullStoryClicked() }
	) {
		Row(
			modifier = Modifier
				.padding(5.dp)
				.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically
		) {
			AsyncImage(
				modifier = Modifier
					.height(120.dp) // Adjust the height as needed
					.width(120.dp),
				model = ImageRequest.Builder(LocalContext.current)
					.data(article.urlToImage)
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
						.background(chip.onErrorContainer, shape = RoundedCornerShape(4.dp))
				){
					Spacer(modifier = Modifier.height(2.dp))
					Text(
						text = article.source.name?: "Google News",
						fontSize = 12.sp,
						color = Color.Gray,
						fontWeight = FontWeight.Bold
					)}
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = article.title,
					style = MaterialTheme.typography.bodyMedium.copy(),
					color = Color.Black,
					maxLines = 1,
					fontSize = 14.sp,
					overflow = TextOverflow.Ellipsis
				)
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = article.content?:"none",
					fontSize = 14.sp,
					maxLines = 2,
					color = Color.Gray
				)

				Row(
					modifier = Modifier
						.fillMaxWidth()
					, verticalAlignment = Alignment.CenterVertically) {
					Text(
						text = article.author?.split(",")?.firstOrNull() ?: "Source",
						style = MaterialTheme.typography.bodySmall,
						fontWeight = FontWeight.Bold,
						overflow = TextOverflow.Ellipsis
					)
					Spacer(modifier = Modifier.width(20.dp))
					Text(
						text = ".",
						color = Color.Black,
						fontWeight = FontWeight.Bold,
					)

					Spacer(modifier = Modifier.width(15.dp))
					Icon(
						painter = painterResource(id = R.drawable.ic_time),
						contentDescription = null,
						modifier = Modifier.size(5.dp),
						tint = colorResource(id = R.color.body)
					)
					Spacer(modifier = Modifier.width(Dimens.ExtraSmallPadding1))
					Text(
						text = date,
						style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
						color = Color.Black
					)

				}
			}
		}
	}
}



@Composable
fun TintedTextButton(
	isSelected: Boolean,
	category: String,
	onClick: () -> Unit
) {
	val selectedBackgroundColor = Color.Transparent
	val unselectedBackgroundColor = Color.DarkGray
	val border = MaterialTheme.colorScheme

	val textbgShape = RoundedCornerShape(12.dp)
	val TextbtnColor = if (isSelected) selectedBackgroundColor else unselectedBackgroundColor
	val txtBgColor = if (!isSelected) Color.White else Color.Black

	if (isSelected) {
		TextButton(
			onClick = onClick,
			shape = textbgShape,
			border = BorderStroke(1.dp, color = Color.DarkGray),
			colors = ButtonDefaults.buttonColors(
				contentColor = Color.Gray
			),
			modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp)
		) {
			Text(
				text = category,
				fontSize = 14.sp,
				fontWeight = FontWeight.Bold,
			)
		}
	} else {
		TextButton(
			onClick = onClick,
			shape = textbgShape,
			colors = ButtonDefaults.buttonColors(
				contentColor = txtBgColor
			),
			modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp)
		) {
			Text(
				text = category,
				fontSize = 14.sp,
				fontWeight = FontWeight.Bold,
				color = txtBgColor
			)
		}
	}
}

@Composable
fun SearchBar() {
	val textState = remember { mutableStateOf(TextFieldValue()) }
	TextField(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 20.dp)
			.clip(RoundedCornerShape(18.dp)),
		value = textState.value,
		onValueChange = { textState.value = it },
		leadingIcon = {
			Icon(
				imageVector = Icons.Filled.Search,
				contentDescription = "Search Icon",
				tint = Color.Gray
			)
		},
		colors = TextFieldDefaults.colors(

			disabledIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent,
			focusedIndicatorColor = Color.Transparent
		),
		placeholder = {
			Text(text = "Tourism", color = Color.LightGray, fontSize = 16.sp)
		},
		trailingIcon = {
			IconButton(onClick = { /*TODO*/ }) {
				Icon(
					imageVector = Icons.Filled.List,
					contentDescription = "Filter Icon",
					tint = Color.Gray
				)
			}
		}
	)
}


@Preview(showBackground = true)
@Composable
fun ArticleScreenPreview() {
	val articleStates = ArticleStates() // Initialize with relevant data
	ArticleScreen(
		state = articleStates,
		onEvent = {},
		onReadFullStoryButtonClick = {}
	)
}