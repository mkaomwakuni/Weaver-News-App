package dev.mkao.weaver.presentation.languages

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.mkao.weaver.presentation.languages.LanguageConstants.countryMap
import dev.mkao.weaver.presentation.languages.LanguageConstants.languages
import dev.mkao.weaver.presentation.home.ArticleViewModel
import dev.mkao.weaver.presentation.home.state.ArticleEvent

@Composable
fun LanguageEditionsSidebar(
    navController: NavController,
    viewModel: ArticleViewModel,
    onLanguageSelected: (languageCode: String, countryCode: String) -> Unit) {

    var selectedEdition by remember {
        mutableStateOf(
            languages.find { it.code == viewModel.state.value.selectedLanguage }
                ?: languages.find { it.code == "en" }
        )
    }
    var selectedCountryCode by remember {
        mutableStateOf(
            viewModel.state.value.selectedCountry?.abbreviations?.firstOrNull() ?: "US"
        )
    }

    val countryLanguageList = remember {
        languages.flatMap { edition ->
            edition.abbreviations.mapNotNull { countryCode ->
                countryMap[countryCode]?.let { countryName ->
                    Triple(
                        edition.code,
                        countryCode,
                        countryName)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Text(
                text = "Select Edition by Country",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }

        LazyColumn {
            itemsIndexed(countryLanguageList) { index, (languageCode, countryCode, countryName) ->
                CountryItem(
                    countryCode = countryCode,
                    countryName = countryName,
                    isSelected = languageCode == selectedEdition?.code && countryCode == selectedCountryCode,
                    onClick = {
                        selectedEdition = languages.find { it.code == languageCode }
                        selectedCountryCode = countryCode

                        val edition = selectedEdition ?: languages.find { it.code == "en" }
                        if (edition != null) {
                            // save the selection in preferences
                            viewModel.onEvent(
                                ArticleEvent.CountryLanguageChanged(
                                    edition,
                                    languageCode
                                )
                            )
                            // Navigate back after selection
                            navController.navigateUp()
                        }

                        onLanguageSelected(languageCode, countryCode)
                    }
                )
                if (index < countryLanguageList.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant)
                    )
                }
            }
        }
    }
}

@Composable
fun CountryItem(
    countryCode: String,
    countryName: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp)
            .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        AsyncImage(
            model = "https://flagsapi.com/$countryCode/flat/64.png",
            contentDescription = "$countryName flag",
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = countryName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
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
//            modifier = Modifier,
//            onLanguageSelected = { _, _ -> },
//        )
//    }
//}