package dev.mkao.weaver.ui.article

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticleUi(
	url: String?,
	onBackPressed: () -> Unit
){
    val context = LocalContext.current
	var isLoading by remember {
		mutableStateOf(true)
	}
	Scaffold (
		modifier = Modifier.fillMaxSize(),
	topBar = {
		TopAppBar(
			title = { Text(text = "Article", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center) },
			actions = {
				IconButton(onClick = onBackPressed) {
					Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="Back" )
				}
			},
			colors = TopAppBarDefaults.topAppBarColors(
				containerColor = MaterialTheme.colorScheme.primaryContainer,
				titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
				actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
			)
		)
	  }
	){paddingValues ->  
		Box(modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues),
		contentAlignment = Alignment.Center) {
			AndroidView(factory = {
				WebView(context).apply {
					webViewClient = object: WebViewClient() {
						override fun onPageFinished(view: WebView?, url: String?) {
							isLoading = false
						}
					}
					loadUrl(url?:"")
				}
			})
			if (isLoading && url !== null){
				CircularProgressIndicator()
			}
		}
		
	}
}