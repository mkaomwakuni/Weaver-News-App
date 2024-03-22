package dev.mkao.weaver.presentation.home
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mkao.weaver.presentation.common.ArticleStates
import dev.mkao.weaver.presentation.common.BottomDialog
import dev.mkao.weaver.presentation.common.CardArtiCle
import dev.mkao.weaver.presentation.common.EventsHolder
import dev.mkao.weaver.presentation.search.SearchAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn( ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
	state: ArticleStates ,
	onReadFullStoryButtonClick: (String) -> Unit,
	onEvent: (EventsHolder) -> Unit) {

	val coroutineScope = rememberCoroutineScope()
	val categories = mutableListOf(
		"General",
		"Business",
		"Health",
		"Science",
		"Sports",
		"Technology",
		"Entertainment"
	)
	var shouldBottomSheetShow by remember { mutableStateOf(false) }
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	var isLoading by remember {	mutableStateOf(true)}

	LaunchedEffect(true) {
		onEvent(EventsHolder.OnCategoryClicked("General"))
	}

	SearchAppBar(onSearchCategoryChanged = {
		onEvent(EventsHolder.OnSearchCategoryChanged(searchRequest = it))
	},
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
					.fillMaxWidth()
					.padding(top = 100.dp),
				verticalArrangement = Arrangement.SpaceBetween,
				horizontalAlignment = Alignment.Start
			) {
				Text(
					modifier = Modifier
						.padding(horizontal = 10.dp),
					text = "Discovery",
					color = Color.Black,
					fontSize = 30.sp,
					fontWeight = FontWeight.Bold,
				)
				Spacer(modifier = Modifier.height(5.dp))
				Text(
					modifier = Modifier
						.padding(horizontal = 10.dp),
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
					.background(color = Color.White)
			) {
				Spacer(modifier = Modifier.height(190.dp))

				SearchAppBar(
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
				if (state.isLoading) {
					repeat(5) { index ->
						ArticleCardShimmerEffect(
							modifier = Modifier
								.fillMaxWidth()
								.padding(horizontal = 16.dp, vertical = 8.dp))
					}

				}else

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
				// Simulate loading delay
				LaunchedEffect(true) {
					delay(3000) // Change this to simulate actual loading time
					isLoading = false
				}
			}
		}
	)
}

	@Composable
	fun TintedTextButton(
		isSelected: Boolean = true,
		category: String,
		onClick: () -> Unit
	) {

		val selectedBackgroundColor = if (isSelected) Color.Transparent else Color.DarkGray
		val txtBgColor = if (!isSelected) Color.White else Color.Black
		val textBgShape = RoundedCornerShape(12.dp)

		if (isSelected) {
			TextButton(
				onClick = onClick,
				shape = textBgShape,
				border = BorderStroke(2.dp, color = Color.DarkGray),
				colors = ButtonDefaults.buttonColors(
					containerColor = selectedBackgroundColor
				),
				modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp)
			) {
				Text(
					text = category,
					color = Color.DarkGray,
					fontSize = 14.sp,
					fontWeight = FontWeight.Bold,
				)
			}
		} else {
			TextButton(
				onClick = onClick,
				shape = textBgShape,
				colors = ButtonDefaults.buttonColors(
					contentColor = txtBgColor
				),
				modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp)
			)
			{
				Text(
					text = category,
					fontSize = 14.sp,
					fontWeight = FontWeight.Bold,
					color = txtBgColor
				)
			}
		}
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