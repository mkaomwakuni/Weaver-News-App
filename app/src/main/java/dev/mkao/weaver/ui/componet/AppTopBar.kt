package dev.mkao.weaver.ui.componet

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar (
	onSearchIconClick : () -> Unit,
	scrollBehavior: TopAppBarScrollBehavior
) {
	TopAppBar(
		scrollBehavior = scrollBehavior,
		title = { Text(text = "Weaver", fontWeight = FontWeight.Bold)},
		actions = {
			IconButton(onClick = onSearchIconClick) {
			Icon(imageVector = Icons.Default.Search, contentDescription ="Search" )
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer,
			titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
			actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
		)
	)
}