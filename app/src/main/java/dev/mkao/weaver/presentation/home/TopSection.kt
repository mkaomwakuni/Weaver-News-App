package dev.mkao.weaver.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.EventsHolder
import dev.mkao.weaver.presentation.common.ArticleCardShimmerEffect
import dev.mkao.weaver.presentation.common.BottomDialog
import dev.mkao.weaver.presentation.common.BottomNavigationBar
import dev.mkao.weaver.presentation.common.CardArtiCle
import dev.mkao.weaver.presentation.common.CardArtiCleTop
import dev.mkao.weaver.presentation.common.StatusbarEffect
import dev.mkao.weaver.viewModels.ArticleStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSection(
    state: ArticleStates,
    navController: NavController,
    onReadFullStoryButtonClick: (Article) -> Unit,
    onEvent: (EventsHolder) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var shouldBottomSheetShow by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var isLoading by remember { mutableStateOf(true) }


    StatusbarEffect()


    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false },
            sheetState = sheetState,
            content = {
                state.isSelected?.let {
                    BottomDialog(
                        article = it,
                        onReadFullStoryButtonClicked = {
                            onReadFullStoryButtonClick(it)
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
            TopAppBar(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 50.dp)
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(12.dp)),
                title = { /* Optional title content */ },
                navigationIcon = {
                    IconButton(
                        onClick = { /* Handle menu icon click */ }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = stringResource(R.string.menu)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Handle search icon click */ }
                    ) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = stringResource(
                            R.string.search
                        )
                        )
                    }
                    IconButton(
                        onClick = { /* Handle notification icon click */ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = stringResource(R.string.notifications)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)){
                    Text(
                        text = stringResource(R.string.Briefing),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.view_all),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )
                }

                if (state.isLoading) {
                    LazyRow(
                        modifier = Modifier.height(300.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(5) {
                            ArticleCardShimmerEffect(
                                modifier = Modifier
                                    .height(300.dp)
                                    .width(550.dp)
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }
                } else {
                    LazyRow(
                        modifier = Modifier.height(300.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.sportsArticles) { article ->
                            CardArtiCleTop(
                                article = article,
                                onReadFullStoryClicked = {
                                    shouldBottomSheetShow = true
                                    onEvent(EventsHolder.OnArticleCardClicked(article))
                                }
                            )
                        }
                    }
                }
                LaunchedEffect(true) {
                    delay(4000)
                    isLoading = false
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)){
                Text(
                    text = stringResource(R.string.recommendations),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.view_all),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )
                }

                if (state.isLoading) {
                    repeat(5) {
                        ArticleCardShimmerEffect(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(10.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(state.entertainmentArticles) { article ->
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
                    delay(4000)
                    isLoading = false
                }
            }
        }
    )
}