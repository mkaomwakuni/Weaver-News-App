package dev.mkao.weaver.presentation.languageedition

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.LanguageEdition
import kotlinx.coroutines.delay

@Composable
fun LanguageEditionsSidebar(
    navController: NavController,
    modifier: Modifier = Modifier,
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedEdition by remember {  mutableStateOf<LanguageEdition?>( null) }
    val languages = remember {
        listOf(
            LanguageEdition("ar", "Arabic", "العربية"),
            LanguageEdition("zh", "Chinese", "中文"),
            LanguageEdition("nl", "Dutch", "Nederlands"),
            LanguageEdition("en", "English", "English"),
            LanguageEdition("fr", "French", "Français"),
            LanguageEdition("de", "German", "Deutsch"),
            LanguageEdition("el", "Greek", "Ελληνικά"),
            LanguageEdition("he", "Hebrew", "עברית"),
            LanguageEdition("hi", "Hindi", "हिन्दी"),
            LanguageEdition("it", "Italian", "Italiano"),
            LanguageEdition("ja", "Japanese", "日本語"),
            LanguageEdition("ml", "Malayalam", "മലയാളം"),
            LanguageEdition("mr", "Marathi", "मराठी"),
            LanguageEdition("no", "Norwegian", "Norsk"),
            LanguageEdition("pt", "Portuguese", "Português"),
            LanguageEdition("ro", "Romanian", "Română"),
            LanguageEdition("ru", "Russian", "Русский"),
            LanguageEdition("es", "Spanish", "Español"),
            LanguageEdition("sv", "Swedish", "Svenska"),
            LanguageEdition("ta", "Tamil", "தமிழ்"),
            LanguageEdition("te", "Telugu", "తెలుగు"),
            LanguageEdition("uk", "Ukrainian", "Українська")
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)) {
            Text(
                text = "Editions",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(1.4f),
                painter = painterResource(id = R.drawable.world),
                contentDescription = "",
                contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
            )
        }

        LazyColumn {
            itemsIndexed(languages) { index, language ->
                LanguageItem(
                    language = language,
                    onLanguageSelected = {
                        selectedEdition = it
                        onLanguageSelected(it.code)
                    },
                    isLanguageSelected = language == selectedEdition
                )
                if (index < languages.size - 1) {
                    HorizontalDivider()
                }
                LaunchedEffect(selectedEdition) {
                    delay(3000)
                    onDismiss()
                }
            }
        }
    }
}

@Composable
fun LanguageItem(
    language: LanguageEdition,
    onLanguageSelected: (LanguageEdition) -> Unit,
    isLanguageSelected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLanguageSelected(language) }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        if (isLanguageSelected) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 6.dp),
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = language.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = language.nativeName,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
//@Preview(showBackground = true)
//@Composable
//fun LanguageEditionsSidebarPreview() {
//    val navController = NavController(LocalContext.current)
//    MaterialTheme {
//        LanguageEditionsSidebar(
//            navController = navController,
//            onLanguageSelected = { /* Handle language selection */ }
//        )
//    }
//}