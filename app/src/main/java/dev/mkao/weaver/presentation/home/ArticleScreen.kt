package dev.mkao.weaver.presentation.home
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.presentation.common.ArticleStates
import dev.mkao.weaver.presentation.common.BottomDialog
import dev.mkao.weaver.presentation.common.DateFormat
import dev.mkao.weaver.presentation.common.EventsHolder
import dev.mkao.weaver.util.Dimens
import kotlinx.coroutines.launch

@OptIn( ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
	state: ArticleStates,
	onReadFullStoryButtonClick: (String) -> Unit,
	onEvent: (EventsHolder) -> Unit
) {

	val coroutineScope = rememberCoroutineScope()
	val categories = mutableListOf("General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment")
	var shouldBottomSheetShow by remember { mutableStateOf(false) }
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

	SearchBar(onSearchCategoryChanged = {
		onEvent(EventsHolder.OnSearchCategoryChanged(searchRequest = it))},
		onKeyboardDismissed = {})

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
			Spacer(modifier = Modifier.height(50.dp))

			Column(
				modifier = Modifier
					.padding(start = 7.dp)
					.fillMaxWidth()
					.padding(top = 100.dp),
				verticalArrangement = Arrangement.SpaceBetween,
				horizontalAlignment = Alignment.Start
			) {
				Text(
					text = "Discovery",
					color = Color.Black,
					fontSize = 30.sp,
					fontWeight = FontWeight.Bold,
				)
				Spacer(modifier = Modifier.height(5.dp))
				Text(
					text = "News from all around the world",
					color = Color.LightGray,
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold,
				)
			}

		},
		content = {
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(horizontal = 7.dp)
					.background(color = Color.White)
			) {
				Spacer(modifier = Modifier.height(190.dp))

				SearchBar(
					onSearchCategoryChanged = {},
					onKeyboardDismissed = {}
				)

				Spacer(modifier = Modifier.height(10.dp))
				LazyRow(
					contentPadding = PaddingValues(horizontal = 2.dp),
					horizontalArrangement = Arrangement.spacedBy(2.dp)
				) {
					items(categories.size) { index ->
						val category = categories[index]
						val isSelected = state.category == category
						TintedTextButton(
							isSelected = isSelected,
							category = category,
							onClick = { onEvent(EventsHolder.OnCategoryClicked(category)) }
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
						CardArtiCle(
							article = article,
							onReadFullStoryClicked = {
								shouldBottomSheetShow = true
								onEvent(EventsHolder.OnArticleCardClicked(article))
							}
						)
						Spacer(modifier = Modifier.height(0.5.dp))
					}

				}
			}
		}
	)
}

@Composable
fun CardArtiCle(
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
						.background(chip.onPrimaryContainer, shape = RoundedCornerShape(4.dp))
				){
					Spacer(modifier = Modifier.height(2.dp))
					Text(
						text = article.source.name?: "Google News",
						fontSize = 12.sp,
						color = Color.White,
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
						modifier = Modifier.size(15.dp),
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
	isSelected: Boolean = true,
	category: String,
	onClick: () -> Unit
) {

	val selectedBackgroundColor = if (isSelected) Color.Transparent else Color.Blue
	val txtBgColor = if (!isSelected) Color.White else Color.Black
	val textBgShape = RoundedCornerShape(12.dp)

	if (isSelected) {
		TextButton(
			onClick = onClick,
			shape = textBgShape,
			border = BorderStroke(1.dp, color = Color.DarkGray),
			colors = ButtonDefaults.buttonColors(
				contentColor = Color.Black,
				containerColor = selectedBackgroundColor
			),
			modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp)
		) {
			Text(
				text = category,
				color = Color.Blue,
				fontSize = 14.sp,
				fontWeight = FontWeight.Bold,
			)
		}
	} else {
		TextButton(
			onClick = onClick,
			shape = textBgShape,
			colors = ButtonDefaults.buttonColors(
				contentColor = txtBgColor,
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
fun SearchBar(
	onSearchCategoryChanged: (String) -> Unit,
	onKeyboardDismissed: () -> Unit
) {
	val textState = remember { mutableStateOf(TextFieldValue()) }

	TextField(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 20.dp)
			.height(60.dp)
			.clip(RoundedCornerShape(18.dp)),
		value = textState.value,
		onValueChange = { textState.value = it },
		keyboardActions = KeyboardActions(
			onDone = {
				onSearchCategoryChanged(textState.value.text)
				onKeyboardDismissed()
			}
		),
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
			IconButton(onClick = onKeyboardDismissed) {
				Icon(
					imageVector = Icons.AutoMirrored.Filled.List,
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