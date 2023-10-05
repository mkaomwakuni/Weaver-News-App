package dev.mkao.weaver.ui

import BottomDialog
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.ui.componet.AppTopBar
import dev.mkao.weaver.ui.componet.ErrorHandler
import dev.mkao.weaver.ui.componet.NewsCategories
import dev.mkao.weaver.ui.componet.SearchBar
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
	 Column (modifier = Modifier.fillMaxSize()) {
		 Crossfade(
			 targetState = states.isSearchBarVisible,
			 label = "SearchBarVisibilityCrossfade"){ isVisible ->
			 if (isVisible){
				 Column {
					 SearchBar(
						 value = "",
						 onInputValueChange = {},
						 onCloseClicked = {},
						 onSearchIconClicked = {}
					 )

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
			 }else{
				 Scaffold(
					 modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
					 topBar = {
						 AppTopBar(
							 scrollBehavior = scrollBehavior,
							 onSearchIconClick = {
								 onUserEvent(EventsHolder.OnSearchIconClicked)
							 })
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
