package dev.mkao.weaver.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TintedTextButton(
    isSelected: Boolean = true,
    category: String,
    onClick: () -> Unit
) {
    val selectedBackgroundColor = if (isSelected) Color.Transparent else Color.DarkGray
    val txtBgColor = if (!isSelected) Color.White else Color.Black
    val textBgShape = RoundedCornerShape(12.dp)

    TextButton(
        onClick = onClick,
        shape = textBgShape,
        border = if (isSelected) BorderStroke(2.dp, color = Color.DarkGray) else null,
        colors = ButtonDefaults.buttonColors(
            containerColor = selectedBackgroundColor
        ),
        modifier = Modifier
            .padding(vertical = 4.dp,horizontal = 10.dp)
    ) {
        Text(
            text = category,
            color = txtBgColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}