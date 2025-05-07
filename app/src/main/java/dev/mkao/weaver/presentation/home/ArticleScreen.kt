package dev.mkao.weaver.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.presentation.bookmarks.BookmarkViewModel
import dev.mkao.weaver.presentation.common.ArticleCardShimmerEffect
import dev.mkao.weaver.presentation.common.BottomDialog
import dev.mkao.weaver.presentation.common.BottomNavigationBar
import dev.mkao.weaver.presentation.common.CardArtiCle
import dev.mkao.weaver.presentation.common.TintedTextButton
import dev.mkao.weaver.presentation.home.state.ArticleEvent
import dev.mkao.weaver.presentation.home.state.ArticleState
import dev.mkao.weaver.presentation.search.SearchAppBar
import dev.mkao.weaver.util.NewsCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    navController: NavController,
    state: ArticleState,
    bookmarkViewModel: BookmarkViewModel,
    onReadFullStoryButtonClick: (Article) -> Unit,
    onEvent: (ArticleEvent) -> Unit
) {
    val screenState = rememberArticleScreenState()
    LaunchedEffect(Unit) {
        if (state.category.isEmpty()) {
            onEvent(
                ArticleEvent.CategorySelected(
                    NewsCategories.General.apiValue
                )
            )
        }
    }

    Scaffold(
        topBar = { ArticleScreenTopBar() },
        bottomBar = { BottomNavigationBar(navController = navController) },
        content = { paddingValues ->
            ArticleScreenContent(
                state = state,
                screenState = screenState,
                paddingValues = paddingValues,
                onEvent = onEvent
            )
        }
    )

    if (screenState.shouldBottomSheetShow) {
        ArticleBottomSheet(
            article = state.selectedArticle,
            sheetState = screenState.sheetState,
            onDismiss = { screenState.shouldBottomSheetShow = false },
            onReadFullStoryButtonClick = { article ->
                onReadFullStoryButtonClick(article)
                screenState.hideBottomSheet()
            },
            bookmarkViewModel = bookmarkViewModel
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
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
    IconButton(
        onClick = {

        }
    ) {
        Icon(
            imageVector = Icons.Filled.Notifications,
            contentDescription = "Notifications",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
private fun ArticleScreenContent(
    state: ArticleState,
    screenState: ArticleScreenState,
    paddingValues: PaddingValues,
    onEvent: (ArticleEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        SearchAppBar(
            modifier = Modifier.focusRequester(
                screenState.focusRequester
            ),
            value = state.searchQuery,
            onValueChange = { newQuery ->
                onEvent(
                    ArticleEvent.SearchQueryChanged(newQuery)
                )
            },
            onCloseIconClicked = {
                onEvent(
                    ArticleEvent.CloseSearch
                )
            },
            onSearchClicked = screenState::hideKeyboardAndClearFocus
        )

        Spacer(modifier = Modifier.height(2.dp))

        CategorySelector(
            categories = screenState.categories,
            selectedCategory = NewsCategories.values().find {
                it.apiValue == state.category
            } ?: NewsCategories.General,
            onEvent = onEvent,
            currentCategory = state.category
        )

        if (state.isLoading) {
            ArticleLoadingShimmer()
        } else {
            ArticleList(
                articles = state.articles,
                onArticleClicked = { article ->
                    screenState.shouldBottomSheetShow = true
                    onEvent(ArticleEvent.ArticleSelected(article))
                }
            )
        }
    }
}

@Composable
private fun CategorySelector(
    categories: List<NewsCategories>,
    selectedCategory: NewsCategories,
    onEvent: (ArticleEvent) -> Unit,
    currentCategory: String
) {
    val articleViewModel: ArticleViewModel = hiltViewModel()
    LazyRow(
        contentPadding = PaddingValues(horizontal = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            TintedTextButton(
                isSelected = category.apiValue == currentCategory,
                category = category.displayName,
                onClick = {
                    articleViewModel.updateCategoryAndFetchArticles(
                        category.apiValue,
                        category.displayName
                    )
                    onEvent(
                        ArticleEvent.CategorySelected(
                            category.apiValue
                        )
                    )
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
    bookmarkViewModel: BookmarkViewModel,
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
                onReadFullStoryButtonClicked = {
                    onReadFullStoryButtonClick(it)
                },
                bookmarkViewModel = bookmarkViewModel
            )
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun rememberArticleScreenState(
    categories: List<NewsCategories> = NewsCategories.values().toList(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
): ArticleScreenState {
    val density = LocalDensity.current
    return remember {
        ArticleScreenState(
            categories = categories,
            coroutineScope = coroutineScope,
            focusManager = focusManager,
            focusRequester = focusRequester,
            keyboardController = keyboardController,
            density = density
        )
    }
}

class ArticleScreenState(
    val categories: List<NewsCategories>,
    private val coroutineScope: CoroutineScope,
    private val focusManager: FocusManager,
    val focusRequester: FocusRequester,
    private val keyboardController: SoftwareKeyboardController?,
    private val density: Density
) {
    var shouldBottomSheetShow by mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    val sheetState = SheetState(skipPartiallyExpanded = false, density = density)

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
