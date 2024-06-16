package dev.mkao.weaver.presentation.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.mkao.weaver.presentation.navigation.Screen


@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var notificationEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf(TextFieldValue("User123")) }
    var isDarkMode by remember { mutableStateOf(false) }
    val context = LocalContext.current


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
                SettingsCard(title = "User") {
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
                SettingsCard(
                    title = "Invite Friends" ,
                    trailing = {
                        Icon(modifier = Modifier.clickable {
                        launchInviteFriendsIntent(context)
                        },
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Arrow forward")
                    }
                )
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
                SettingsCard(
                    title = "Share on Instagram",
                    trailing = {
                    Icon(modifier = Modifier.clickable {
                        launchAppIntent(context, packageName =  "com.instagram.android")
                    },
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow forward")
                            }
                        )


                HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
                SettingsCard(
                    title = "Share on TikTok",
                    trailing = {
                        Icon(modifier = Modifier.clickable {
                        launchAppIntent(context, packageName = "com.zhiliaoapp.musically")
                            },
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Arrow forward"
                        )
                    }
                )

                SectionTitle(title = "Other")
                SettingsCard(title = "Manage Consent")
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
                SettingsCard(
                title = "About App",
                trailing = {
                        Icon(modifier = Modifier.clickable {
                            navController.navigate(Screen.About.route)
                            },
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Arrow forward"
                        )
                    }
                )
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
            modifier = Modifier.clickable {},
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
fun launchInviteFriendsIntent(context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, "Hey, check out this awesome app!")
    context.startActivity(Intent.createChooser(intent, "Invite friends via"))
}

fun launchAppIntent(context: Context, packageName: String) {
    val intent = context.packageManager.getLaunchIntentForPackage(packageName)
    if (intent != null) {
        context.startActivity(intent)
    } else {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
        context.startActivity(intent)
    }
}


