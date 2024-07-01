package dev.mkao.weaver.presentation.common

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.mkao.weaver.R
import dev.mkao.weaver.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var username by remember { mutableStateOf(TextFieldValue("John Doe")) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.settings), fontWeight = FontWeight.Bold) }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 50.dp)
        ) {
            SectionTitle(title = stringResource(R.string.account_and_security))
            SettingsCard(title = stringResource(R.string.user)) {
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

            SectionTitle(title = stringResource(R.string.general))
            SettingsCard(
                title = stringResource(R.string.invite_friends),
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                onTrailingIconClick = { launchInviteFriendsIntent(context) }
            )
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
            SettingsCard(
                title = stringResource(R.string.share_on_instagram),
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                onTrailingIconClick = { launchAppIntent(context, packageName = context.getString(R.string.instagram)) }
            )
            HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
            SettingsCard(
                title = stringResource(R.string.share_on_tiktok),
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                onTrailingIconClick = { launchAppIntent(context, packageName = context.getString(R.string.Tikto)) }
            )

            HorizontalDivider(thickness = 1.dp, color = Color(0xFFFFE4B5))
            SettingsCard(
                title = stringResource(R.string.language),
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                onTrailingIconClick = { /* Add your language selection logic here */ }
            )

            SectionTitle(title = stringResource(R.string.other))

            SettingsCard(
                title = stringResource(R.string.about_app),
                trailingIcon = Icons.AutoMirrored.Filled.ArrowForward,
                onTrailingIconClick = { navController.navigate(Screen.About.route) }
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
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(8.dp),
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
            trailingIcon?.let {
                Icon(
                    modifier = Modifier.clickable { onTrailingIconClick?.invoke() },
                    imageVector = it,
                    contentDescription = stringResource(R.string.arrow_forward)
                )
            }
        }
        content?.invoke()
    }
}

fun launchInviteFriendsIntent(context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.hey_check_out_this_awesome_app))
    }
    context.startActivity(Intent.createChooser(intent,
        context.getString(R.string.invite_friends_via)))
}

fun launchAppIntent(context: Context, packageName: String) {
    val intent = context.packageManager.getLaunchIntentForPackage(packageName)
    if (intent != null) {
        context.startActivity(intent)
    } else {
        val playStoreIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
        context.startActivity(playStoreIntent)
    }
}

fun launchPrivacyPolicyIntent(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(""))
    context.startActivity(intent)
}