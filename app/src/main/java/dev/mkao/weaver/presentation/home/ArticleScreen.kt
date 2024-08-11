package dev.mkao.weaver.presentation.home

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.EventsHolder
import dev.mkao.weaver.presentation.common.ArticleCardShimmerEffect
import dev.mkao.weaver.presentation.common.BottomDialog
import dev.mkao.weaver.presentation.common.BottomNavigationBar
import dev.mkao.weaver.presentation.common.CardArtiCle
import dev.mkao.weaver.presentation.common.TintedTextButton
import dev.mkao.weaver.presentation.search.SearchAppBar
import dev.mkao.weaver.viewModels.ArticleScreenViewModel
import dev.mkao.weaver.viewModels.ArticleStates
import dev.mkao.weaver.viewModels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
	state: ArticleStates,
	navController: NavController,
	onReadFullStoryButtonClick: (Article) -> Unit,
	onEvent: (EventsHolder) -> Unit,
	sharedViewModel: SharedViewModel = hiltViewModel()
) {
	val screenState = rememberArticleScreenState()

	LaunchedEffect(Unit) {
		onEvent(EventsHolder.OnCategoryClicked("Technology"))
	}

	Scaffold(
		topBar = { ArticleScreenTopBar() },
		bottomBar = { BottomNavigationBar(navController = navController) },
		content = { paddingValues ->
			ArticleScreenContent(
				state = state,
				screenState = screenState,
				paddingValues = paddingValues,
				onEvent = onEvent,
				sharedViewModel = sharedViewModel
			)
		}
	)

	if (screenState.shouldBottomSheetShow) {
		ArticleBottomSheet(
			article = state.isSelected,
			sheetState = screenState.sheetState,
			onDismiss = { screenState.shouldBottomSheetShow = false },
			onReadFullStoryButtonClick = { article ->
				onReadFullStoryButtonClick(article)
				screenState.hideBottomSheet()
			}
		)
	}
}

@Composable
private fun ArticleScreenTopBar() {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 70.dp, start = 10.dp, end = 10.dp),
		verticalArrangement = Arrangement.spacedBy(15.dp),
		horizontalAlignment = Alignment.Start
	) {
		Text(
			text = stringResource(R.string.discover),
			fontSize = 32.sp,
			fontWeight = FontWeight.Bold,
		)
		Text(
			text = stringResource(R.string.fresh_stories_and_bold_ideas_to_help_you_live_curiously),
			fontSize = 16.sp,
			fontWeight = FontWeight.Bold,
		)
	}
}

@Composable
private fun ArticleScreenContent(
	state: ArticleStates,
	screenState: ArticleScreenState,
	paddingValues: PaddingValues,
	onEvent: (EventsHolder) -> Unit,
	sharedViewModel: SharedViewModel
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues)
	) {
		SearchAppBar(
			modifier = Modifier.focusRequester(screenState.focusRequester),
			value = state.searchRequest,
			onValueChange = { newQuery -> onEvent(EventsHolder.OnSearchCategoryChanged(newQuery)) },
			onCloseIconClicked = { onEvent(EventsHolder.OnCloseIconClicked) },
			onSearchClicked = screenState::hideKeyboardAndClearFocus
		)

		Spacer(modifier = Modifier.height(2.dp))

		CategorySelector(
			categories = screenState.categories,
			selectedCategory = state.category,
			onEvent = onEvent // Pass the onEvent parameter here
		)

		if (state.isLoading) {
			ArticleLoadingShimmer()
		} else {
			ArticleList(
				articles = state.article,
				onArticleClicked = { article ->
					screenState.shouldBottomSheetShow = true
					onEvent(EventsHolder.OnArticleCardClicked(article))
				}
			)
		}
	}
}

@Composable
private fun CategorySelector(
	categories: List<String>,
	selectedCategory: String,
	onEvent: (EventsHolder) -> Unit
) {
	val articleViewModel: ArticleScreenViewModel = hiltViewModel()
	LazyRow(
		contentPadding = PaddingValues(horizontal = 2.dp),
		horizontalArrangement = Arrangement.spacedBy(2.dp)
	) {
		items(categories) { category ->
			TintedTextButton(
				isSelected = selectedCategory == category,
				category = category,
				onClick = {
					articleViewModel.updateCategoryAndFetchArticles(category)
					onEvent(EventsHolder.OnCategoryClicked(category))
				}
			)
		}
	}
}

@Composable
private fun ArticleLoadingShimmer() {
	repeat(5) {
		ArticleCardShimmerEffect(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp, vertical = 8.dp)
		)
	}
}

@Composable
private fun ArticleList(
	articles: List<Article>,
	onArticleClicked: (Article) -> Unit
) {
	LazyColumn(
		modifier = Modifier.fillMaxWidth(),
		contentPadding = PaddingValues(2.dp),
		verticalArrangement = Arrangement.spacedBy(2.dp)
	) {
		items(articles) { article ->
			CardArtiCle(
				article = article,
				onReadFullStoryClicked = { onArticleClicked(article) }
			)
			Spacer(modifier = Modifier.height(0.5.dp))
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleBottomSheet(
	article: Article?,
	sheetState: SheetState,
	onDismiss: () -> Unit,
	onReadFullStoryButtonClick: (Article) -> Unit
) {
	ModalBottomSheet(
		onDismissRequest = onDismiss,
		sheetState = sheetState,
		dragHandle = null
	) {
		article?.let {
			BottomDialog(
				article = it,
				onReadFullStoryButtonClicked = { onReadFullStoryButtonClick(it) }
			)
		}
	}
}

@Composable
fun rememberArticleScreenState(
	categories: List<String> = listOf("General", "World", "Nation", "Business", "Technology","Science","Health"),
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	focusManager: FocusManager = LocalFocusManager.current,
	focusRequester: FocusRequester = remember { FocusRequester() },
	keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) = remember {
	ArticleScreenState(
		categories = categories,
		coroutineScope = coroutineScope,
		focusManager = focusManager,
		focusRequester = focusRequester,
		keyboardController = keyboardController
	)
}

class ArticleScreenState(
	val categories: List<String>,
	private val coroutineScope: CoroutineScope,
	private val focusManager: FocusManager,
	val focusRequester: FocusRequester,
	private val keyboardController: SoftwareKeyboardController?
) {
	var shouldBottomSheetShow by mutableStateOf(false)
	@OptIn(ExperimentalMaterial3Api::class)
	val sheetState = SheetState(skipPartiallyExpanded = false)

	fun hideKeyboardAndClearFocus() {
		keyboardController?.hide()
		focusManager.clearFocus()
	}

	@OptIn(ExperimentalMaterial3Api::class)
	fun hideBottomSheet() {
		coroutineScope.launch {
			sheetState.hide()
		}.invokeOnCompletion {
			if (!sheetState.isVisible) shouldBottomSheetShow = false
		}
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
