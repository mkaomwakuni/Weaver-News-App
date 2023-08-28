package dev.mkao.weaver.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mkao.weaver.ui.componet.AppTopBar
import dev.mkao.weaver.viewModel.ArticleScreenViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(viewModel: ArticleScreenViewModel = hiltViewModel()) {
	val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			AppTopBar (
				scrollBehavior = scrollBehavior,
				onSearchIconClick = {})
		}
	) {padding ->
	
	val category = "business"
	// Trigger data fetching when the Composable becomes active
	LaunchedEffect(category) {
		viewModel.getNewsArticles(category)
	}
	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(10.dp),
		contentPadding = PaddingValues(10.dp),
		modifier = Modifier.padding(padding)
	) {
		items(viewModel.newsArticles) { newsArticle ->
			ArticleCard(article = newsArticle, onClickingCard = {})
		}
	}
  }
}
