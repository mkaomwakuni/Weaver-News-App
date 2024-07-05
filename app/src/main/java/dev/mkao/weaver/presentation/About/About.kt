package dev.mkao.weaver.presentation.About

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mkao.weaver.R

@Composable
fun About() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 30.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .height(260.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_splash),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(260.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0x80000000))
                        )
                    )
            )
        }
        Text(
            text = stringResource(R.string.AboutTitle),
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Justify,
            color = Color.DarkGray,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.About),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}
