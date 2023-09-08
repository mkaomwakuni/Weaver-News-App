package dev.mkao.weaver.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.ui.componet.AppTopBar
import dev.mkao.weaver.ui.componet.NewsCategories
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ArticleScreen(
	states: ArticleStates,
	onUserEvent: (EventsHolder) -> Unit)
 {
	val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
	val pagerState = rememberPagerState()
	val categories = listOf("General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment")
	val coroutine = rememberCoroutineScope()
	
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
				LazyColumn(
					verticalArrangement = Arrangement.spacedBy(10.dp),
					contentPadding = PaddingValues(10.dp),
				) {
					items(states.article) { newsArticle ->
						ArticleCard(
							article = newsArticle,
							onClickingCard = {}
						)
					}
				}
			}
		}
	}
}

//state
//event
