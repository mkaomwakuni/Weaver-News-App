package dev.mkao.weaver.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mkao.weaver.viewModel.ArticleScreenViewModel


@Composable
fun ArticleScreen ( viewModel: ArticleScreenViewModel = hiltViewModel()) {

	LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp),
	contentPadding = PaddingValues(10.dp)
	){
		items(viewModel.newsArticles){ newsArticle ->
			ArticleCard(article = newsArticle, onClickingCard = {})
		}
	}
}
