package dev.mkao.weaver.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.ViewModel.NewsViewModel


@Composable
fun ArticleScreen (viewModel: NewsViewModel) {

	LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp),
	contentPadding = PaddingValues(10.dp)
	){
		items(viewModel.newsArticles){ newsArticle ->
			Text(text = newsArticle.title)
		
		}
	}
}
