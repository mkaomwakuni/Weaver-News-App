package dev.mkao.weaver.presentation.home

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.presentation.common.ArticleStates
import dev.mkao.weaver.presentation.common.BottomDialog
import dev.mkao.weaver.presentation.common.CardArtiCle
import dev.mkao.weaver.presentation.common.EventsHolder
import dev.mkao.weaver.presentation.common.Retry
import dev.mkao.weaver.presentation.navgraph.BottomNavigationBar
import dev.mkao.weaver.presentation.search.SearchAppBar
import dev.mkao.weaver.presentation.search.SearchCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
	state: ArticleStates,
	navController: NavController,
	onReadFullStoryButtonClick: (String) -> Unit,
	onEvent: (EventsHolder) -> Unit
) {
	val coroutineScope = rememberCoroutineScope()
	val categories = listOf(
		"General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment"
	)
	val focusManager = LocalFocusManager.current
	val focusRequester = remember { FocusRequester() }
	val keyboardController = LocalSoftwareKeyboardController.current
	var shouldBottomSheetShow by remember { mutableStateOf(false) }
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	var isLoading by remember { mutableStateOf(true) }

	LaunchedEffect(true) {
		onEvent(EventsHolder.OnCategoryClicked("General"))
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
					.padding(top = 70.dp),
				verticalArrangement = Arrangement.SpaceBetween,
				horizontalAlignment = Alignment.Start
			) {
				Text(
					modifier = Modifier.padding(horizontal = 10.dp),
					text = stringResource(R.string.discover),
					color = Color.Black,
					fontSize = 34.sp,
					fontWeight = FontWeight.Bold,
				)
				Spacer(modifier = Modifier.height(15.dp))
				Text(
					modifier = Modifier.padding(horizontal = 10.dp),
					text = stringResource(R.string.fresh_stories_and_bold_ideas_to_help_you_live_curiously),
					color = Color.DarkGray,
					fontSize = 15.sp,
					fontWeight = FontWeight.Bold,
				)
			}
		},
		bottomBar = {
			BottomNavigationBar(navController = navController)
		},
		content = { paddingValues ->
			Column(
				modifier = Modifier
					.fillMaxSize()
					.background(color = Color.White)
					.padding(paddingValues)
			) {
				SearchAppBar(
					modifier = Modifier.focusRequester(focusRequester),
					value = state.searchRequest,
					onValueChange = { newQuery ->
						onEvent(EventsHolder.OnSearchCategoryChanged(newQuery))
					},
					onCloseIconClicked = { onEvent(EventsHolder.OnCloseIconClicked) },
					onSearchClicked = {
						keyboardController?.hide()
						focusManager.clearFocus()
					}
				)

				Spacer(modifier = Modifier.height(2.dp))
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
								.padding(horizontal = 16.dp, vertical = 8.dp)
						)
					}
				} else {
					Spacer(modifier = Modifier.height(20.dp))
					LazyColumn(
						modifier = Modifier.fillMaxWidth(),
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
				LaunchedEffect(true) {
					delay(3000)
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

	TextButton(
		onClick = onClick,
		shape = textBgShape,
		border = if (isSelected) BorderStroke(2.dp, color = Color.DarkGray) else null,
		colors = ButtonDefaults.buttonColors(
			containerColor = selectedBackgroundColor
		),
		modifier = Modifier.padding(vertical = 4.dp, horizontal = 10.dp)
	) {
		Text(
			text = category,
			color = txtBgColor,
			fontSize = 14.sp,
			fontWeight = FontWeight.Bold,
		)
	}
}

@Preview(showBackground = true)
@Composable
fun ArticleScreenPreview() {
	val articleStates = ArticleStates() // Initialize with relevant data
	val navController = rememberNavController()
	ArticleScreen(
		state = articleStates,
		navController = navController,
		onEvent = {},
		onReadFullStoryButtonClick = {}
	)
}
