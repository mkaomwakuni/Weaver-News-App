package dev.mkao.weaver.ui

import BottomDialog
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.ui.componet.AppTopBar
import dev.mkao.weaver.ui.componet.ErrorHandler
import dev.mkao.weaver.ui.componet.NewsCategories
import dev.mkao.weaver.ui.componet.SearchBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ArticleScreen(
	state: ArticleStates,
	onEvent: (EventsHolder) -> Unit,
	onReadFullStoryButtonClick: (String) -> Unit
) {
	val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
	val pagerState = rememberPagerState()
	val coroutineScope = rememberCoroutineScope()
	val dynamicColors = MaterialTheme.colorScheme
	val categories = listOf("General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment"	)

	val focusRequester = remember { FocusRequester() }
	val focusManager = LocalFocusManager.current
	val keyboardController = LocalSoftwareKeyboardController.current

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

	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	var shouldBottomSheetShow by remember { mutableStateOf(false) }

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

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(dynamicColors.onPrimary)
	) {
		Crossfade(targetState = state.isSearchBarVisible, label = "") { isVisible ->
			if (isVisible) {
				Column {
					SearchBar(
						modifier = Modifier.focusRequester(focusRequester),
						value = state.SearchRequest,
						onInputValueChange = { newValue ->
							onEvent(EventsHolder.OnSearchCategoryChanged(newValue))
						},
						onCloseClicked = { onEvent(EventsHolder.OnCloseIconClicked) },
						onSearchIconClicked = {
							keyboardController?.hide()
							focusManager.clearFocus()
						}
					)
					NewsArticleList(
						state = state,
						onCardClicked = { article ->
							shouldBottomSheetShow = true
							onEvent(EventsHolder.OnArticleCardClicked(article = article))
						},
						onRetry = {
							onEvent(EventsHolder.OnSearchCategoryChanged(state.SearchRequest))
						}
					)
				}
			} else {
				Scaffold(
					modifier = Modifier
						.nestedScroll(scrollBehavior.nestedScrollConnection)
						.background(dynamicColors.onPrimaryContainer),
					topBar = {
						AppTopBar(
							scrollBehavior = scrollBehavior,
							onSearchIconClick = {
								onEvent(EventsHolder.OnSearchIconClicked)
								coroutineScope.launch {
									delay(500)
									focusRequester.requestFocus()
								}
							}
						)
					}
				) { padding ->
					Column(
						modifier = Modifier
							.fillMaxSize()
							.padding(padding)
					) {
						NewsCategories(
							pagerState = pagerState,
							categories = categories,
							onCategorySelected = { index ->
								coroutineScope.launch { pagerState.animateScrollToPage(index) }
							}
						)
						HorizontalPager(
							pageCount = categories.size,
							state = pagerState
						) {
							NewsArticleList(
								state = state,
								onCardClicked = { article ->
									shouldBottomSheetShow = true
									onEvent(EventsHolder.OnArticleCardClicked(article = article))
								},
								onRetry = {
									onEvent(EventsHolder.OnSearchCategoryChanged(state.category))
								}
							)
						}
					}
				}
			}
		}
	}
}
@Composable
fun NewsArticleList(
	state: ArticleStates,
	onCardClicked: (Article) -> Unit,
	onRetry: () -> Unit,
) {
	LazyColumn(
		contentPadding = PaddingValues(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		items(state.article) { article ->
			ArticleCard(
				article = article,
				onClickingCard = onCardClicked
			)
		}
	}
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		if (state.isLoading) {
			LinearProgressIndicator()
		}
		if (state.error != null) {
			ErrorHandler(
				error = state.error,
				onReload = onRetry
			)
		}
	}
}