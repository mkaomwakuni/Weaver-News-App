package dev.mkao.weaver.presentation.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
            Divider(color = Color(0xFFFFE4B5), thickness = 1.dp)
            SettingsCard(title = "Enable Notifications", trailing = {
                Switch(
                    checked = notificationEnabled,
                    onCheckedChange = { notificationEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.LightGray)
                )
            })
            Divider(color = Color(0xFFFFE4B5), thickness = 1.dp)
            SettingsCard(title = "Enable Dark Mode", trailing = {
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.Red)
                )
            })

            SectionTitle(title = "General")
            SettingsCard(title = "Invite Friends")
            Divider(color = Color(0xFFFFE4B5), thickness = 1.dp)
            SettingsCard(title = "Share on Instagram")
            Divider(color = Color(0xFFFFE4B5), thickness = 1.dp)
            SettingsCard(title = "Share on TikTok")

            SectionTitle(title = "Other")
            SettingsCard(title = "Manage Consent")
            Divider(color = Color(0xFFFFE4B5), thickness = 1.dp)
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
