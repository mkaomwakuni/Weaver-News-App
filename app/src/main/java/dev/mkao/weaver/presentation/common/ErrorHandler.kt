package dev.mkao.weaver.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ErrorHandler(
	error: String,
	onReload: () -> Unit,
	modifier: Modifier = Modifier
) {
	Column (
		modifier = Modifier
	) {
		Text(
				text = error,
				color = Color.Red,
				fontSize = 18.sp
		)
			Spacer(modifier = Modifier.height(8.dp))
			Button(onClick = onReload,modifier = Modifier.align(CenterHorizontally)) {
			Text(text = "Reload")
		}
		
	}
}