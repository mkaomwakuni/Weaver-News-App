package dev.mkao.weaver.presentation.Bookmarks

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var notificationEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf(TextFieldValue("User123")) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold)}
            )
        },
        containerColor = Color(0xFFF0F0F0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SectionTitle(title = "Account and Security")
            SettingsCard(title = "Username") {
                BasicTextField(
                    value = username,
                    onValueChange = { username = it },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
            SettingsCard(title = "Enable Notifications", trailing = {
                Switch(
                    checked = notificationEnabled,
                    onCheckedChange = { notificationEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.LightGray)
                )
            })
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
            SettingsCard(title = "Enable Dark Mode", trailing = {
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.Red)
                )
            })

            SectionTitle(title = "General")
            SettingsCard(title = "Invite Friends")
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
            SettingsCard(title = "Share on Instagram")
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
            SettingsCard(title = "Share on TikTok")

            SectionTitle(title = "Other")
            SettingsCard(title = "Manage Consent")
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
            SettingsCard(title = "About App")
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier
            .padding(vertical = 8.dp)
    )
}

@Composable
fun SettingsCard(
    title: String,
    trailing: @Composable (() -> Unit)? = {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Arrow forward"
        )
    },
    content: @Composable (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 18.sp, modifier = Modifier.weight(1f))
            trailing?.invoke()
        }
        if (content != null) {
            content()
        }
    }
}

@Preview
@Composable
fun SettingPreview() {
    SettingsScreen()
}
