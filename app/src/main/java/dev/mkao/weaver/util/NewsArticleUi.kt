package dev.mkao.weaver.util
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import dev.mkao.weaver.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticleUi(
    url: String?,
    onBackPressed: () -> Unit
){
    var isLoading by remember {
        mutableStateOf(true)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .background(Color.White),

                title = {
                    Box(
                        modifier = Modifier
                            .padding(start = 1.dp)
                            .fillMaxSize(0.8f),
                        contentAlignment = CenterStart,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = null,
                            modifier = Modifier
                                .width(100.dp)
                                .height(30.dp)
                                .padding(start = 60.dp)
                        )

                        Spacer(modifier = Modifier.width(48.dp))
                        Text(
                            text = "Tɦɛ Wɛǟʋɛʀ",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle favorites action */ }) {
                        Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
                    }
                    IconButton(onClick = onBackPressed) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Transparent,
                    actionIconContentColor = Color.Black
                )
            )
        }
    ){paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            contentAlignment = Alignment.Center) {
            AndroidView(factory = {
                WebView(it).apply {

                    // Enable media playback
                    settings.mediaPlaybackRequiresUserGesture = false

                    // WebViewClient to handle page events
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            // Handle page started event
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            // Handle page finished event
                            isLoading = false
                        }
                    }
                    // WebChromeClient to handle JavaScript alerts, confirmations, etc.
                    webChromeClient = object : WebChromeClient() {
                        override fun onReceivedTitle(view: WebView?, title: String?) {
                            // Handle received title event
                        }

                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            // Handle progress changed event
                        }
                    }
                    loadUrl(url ?: "")
                }
            })
            if (isLoading && url !== null){
                CircularProgressIndicator()
            }
        }

    }
}