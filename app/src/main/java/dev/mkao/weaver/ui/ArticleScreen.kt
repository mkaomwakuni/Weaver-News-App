package dev.mkao.weaver.ui

import BottomDialog
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.ui.componet.AppTopBar
import dev.mkao.weaver.ui.componet.ErrorHandler
import dev.mkao.weaver.ui.componet.NewsCategories
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ArticleScreen(
	states: ArticleStates,
	onUserEvent: (EventsHolder) -> Unit,
	onReadFullStoryButtonClick: (String) -> Unit
)
 {
	 val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
	 val pagerState = rememberPagerState()
	 val categories = listOf("General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment")
	 val coroutine = rememberCoroutineScope()
	 
	 val bottomSheetstate = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	 var bottomsheetToshow  by remember { mutableStateOf(false) }
	 if (bottomsheetToshow){
		 ModalBottomSheet(
			 onDismissRequest = { bottomsheetToshow = false },
			 sheetState = bottomSheetstate ,
			 content = {
				 states.isSelected?.let {
					 BottomDialog(
						 article = it,
						 onReadFullStoryButtonClicked = {
							 onReadFullStoryButtonClick(it.url)
							 coroutine.launch {bottomSheetstate.hide() }.invokeOnCompletion {
								 if (!bottomSheetstate.isVisible) bottomsheetToshow = false
							 }
						 })
				 }
			 }
		 )
	 }
	
	LaunchedEffect(key1 = pagerState) {
		snapshotFlow { pagerState.currentPage }
			.collect { page ->
				onUserEvent(EventsHolder.OnCategoryClicked(category = categories[page]))
			}
	}

	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			AppTopBar(
				scrollBehavior = scrollBehavior,
				onSearchIconClick = {})
		}
	) { padding ->
		Column(modifier = Modifier
			.fillMaxSize()
			.padding(padding)) {
			NewsCategories(
				categories = categories,
				pagerState = pagerState,
				onCategorySelected = { index ->
					coroutine.launch { pagerState.animateScrollToPage(index) }
				}
			)
			HorizontalPager(
				pageCount = categories.size,
				state = pagerState
			) {
				NewsArticleBanner(
					states = states,
					onArticleCardClicked = {article ->
						bottomsheetToshow = true
						onUserEvent(EventsHolder.OnArticleCardClicked(article = article)) },
					onReload = {
						onUserEvent(EventsHolder.OnCategoryClicked(states.category))
					}
				)
			}
		}
	}
}
@Composable
fun NewsArticleBanner(
	states: ArticleStates,
	onReload: () -> Unit,
	onArticleCardClicked: (Article) -> Unit
){
	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(15.dp),
		contentPadding = PaddingValues(15.dp)
	){
		items(states.article){ article ->
			ArticleCard(
				article =article ,
				onClickingCard = onArticleCardClicked
			)
		}
	}
	Box(
		modifier = Modifier.fillMaxSize(),
	    contentAlignment = Alignment.Center
	) {
		if (states.isLoading){
			LinearProgressIndicator()
		}
		if (states.error!=null){
			ErrorHandler(error = states.error, onReload = onReload)
		}
	}
}
