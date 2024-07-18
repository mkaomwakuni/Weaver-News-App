
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.mkao.weaver.R
import dev.mkao.weaver.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item { SectionHeader("General") }
            item { SettingsItem("Language") }
            item { SettingsItem("Notification Settings") }
            item { SettingsItemWithToggle("Alerts") }
            item { SectionHeader("Account & Security") }
            item { SettingsItem("Email and Mobile Number") }
            item { SettingsItem("Security Settings") }
            item { SettingsItem("Delete Account") }
            item { SectionHeader("Other") }
            item { SettingsItem("About Weaver App") {navController.navigate(Screen.About.route)} }
            item { SettingsItem("Privacy Policy") }
            item { SettingsItem("Terms and Conditions") }
            item { SectionHeader("Share") }
            item { SettingsItem("Share via WhatsApp") { launchShareIntent(navController.context, "com.whatsapp") } }
            item { SettingsItem("Share via Facebook") { launchShareIntent(navController.context, "com.facebook.katana") } }
            item { SettingsItem("Share via Twitter") { launchShareIntent(navController.context, "com.twitter.android") } }
            item { SettingsItem("Share via Email") { launchShareIntent(navController.context, "com.google.android.gm") } }
            item {
                Text(
                    text = "v1.1",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}


@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp),
        color = Color.Gray
    )
}

@Composable
fun SettingsItem(title: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Icon(
            Icons.Filled.KeyboardArrowRight,
            contentDescription = "Navigate",
            tint = Color.Gray
        )
    }
}

@Composable
fun SettingsItemWithToggle(title: String) {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF2196F3))
        )
    }
}

// Utility functions (moved from the original package)

fun launchInviteFriendsIntent(context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.hey_check_out_this_awesome_app))
    }
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.invite_friends_via)))
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

fun launchShareIntent(context: Context, packageName: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.hey_check_out_this_awesome_app))
        setPackage(packageName)
    }
    try {
        context.startActivity(shareIntent)
    } catch (e: Exception) {
        launchAppIntent(context, packageName)
    }
}