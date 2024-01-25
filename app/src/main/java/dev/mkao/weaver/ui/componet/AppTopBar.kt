package dev.mkao.weaver.ui.componet

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.google.android.material.color.DynamicColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar (
	onSearchIconClick: () -> Unit,
	scrollBehavior: TopAppBarScrollBehavior
) {
	val dynamicColors = MaterialTheme.colorScheme

	TopAppBar(
		scrollBehavior = scrollBehavior,
		title = { Text(text = "WEAVER NEWS ", fontWeight = FontWeight.Bold) },
		actions = {
			IconButton(onClick = onSearchIconClick) {
				Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = dynamicColors.primaryContainer,
			titleContentColor = dynamicColors.onPrimaryContainer,
			actionIconContentColor = dynamicColors.onPrimaryContainer
		)
	)
}