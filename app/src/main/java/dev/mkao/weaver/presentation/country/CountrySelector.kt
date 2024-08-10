package dev.mkao.weaver.presentation.country

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.mkao.weaver.domain.model.Country
import dev.mkao.weaver.viewModels.SharedViewModel


@Composable
fun CountrySelector(
    onCountrySelected: (Country) -> Unit,
    sharedViewModel: SharedViewModel,
    onDismiss: () -> Unit,
    initialSelectedCountry: Country? = getDefaultCountry()
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf<Country?>(initialSelectedCountry) }
    val selected by sharedViewModel.selectedCountry.collectAsState()

    LaunchedEffect(selected) {
        if (selected == null) {
            selectedCountry = selected
        }
    }

    val countries = getCountries().filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        TopBar(onBackPress = onDismiss)
        SearchBar(searchQuery = searchQuery, onSearchQueryChange = { searchQuery = it })
        selectedCountry?.let {
            CountryList(
                countries = countries,
                selectedCountry = it,
                onCountrySelected = { country ->
                    sharedViewModel.setSelectedCountry(country)
                    selectedCountry = country
                    onCountrySelected(country)

                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onBackPress: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(top = 30.dp),
        title = { Text("Select Country", color = Color(0xFF4CAF50)) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            IconButton(onClick = { onBackPress() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.LightGray) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(8.dp)
    )
}

fun getDefaultCountry(): Country {
    return Country("US", "United States")
}

fun getCountries(): List<Country> {
    return listOf(
        Country("US", "United States"),
        Country("AE", "United Arab Emirates"),
        Country("AR", "Argentina"),
        Country("AT", "Austria"),
        Country("AU", "Australia"),
        Country("BE", "Belgium"),
        Country("BG", "Bulgaria"),
        Country("BR", "Brazil"),
        Country("CA", "Canada"),
        Country("CH", "Switzerland"),
        Country("CN", "China"),
        Country("CO", "Colombia"),
        Country("CU", "Cuba"),
        Country("CZ", "Czech Republic"),
        Country("DE", "Germany"),
        Country("EG", "Egypt"),
        Country("FR", "France"),
        Country("GB", "United Kingdom"),
        Country("GR", "Greece"),
        Country("HK", "Hong Kong"),
        Country("HU", "Hungary"),
        Country("ID", "Indonesia"),
        Country("IE", "Ireland"),
        Country("IL", "Israel"),
        Country("IN", "India"),
        Country("IT", "Italy"),
        Country("JP", "Japan"),
        Country("KR", "South Korea"),
        Country("LT", "Lithuania"),
        Country("LV", "Latvia"),
        Country("MA", "Morocco"),
        Country("MX", "Mexico"),
        Country("MY", "Malaysia"),
        Country("NG", "Nigeria"),
        Country("NL", "Netherlands"),
        Country("NO", "Norway"),
        Country("NZ", "New Zealand"),
        Country("PH", "Philippines"),
        Country("PL", "Poland"),
        Country("PT", "Portugal"),
        Country("RO", "Romania"),
        Country("RS", "Serbia"),
        Country("RU", "Russia"),
        Country("SA", "Saudi Arabia"),
        Country("SE", "Sweden"),
        Country("SG", "Singapore"),
        Country("SI", "Slovenia"),
        Country("SK", "Slovakia"),
        Country("TH", "Thailand"),
        Country("TR", "Turkey"),
        Country("TW", "Taiwan"),
        Country("UA", "Ukraine"),
        Country("VE", "Venezuela"),
        Country("ZA", "South Africa")
    )
}

@Composable
fun CountryList(
    countries: List<Country>,
    selectedCountry: Country,
    onCountrySelected: (Country) -> Unit
) {
    LazyColumn {
        itemsIndexed(countries) { index, country ->
            CountryItem(
                country = country,
                isSelected = country == selectedCountry,
                onCountrySelected = {
                    onCountrySelected(it)
                }
            )
            if (index < countries.size + 1) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun CountryItem(
    country: Country,
    isSelected: Boolean,
    onCountrySelected: (Country) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCountrySelected(country) }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://flagsapi.com/${country.code}/flat/64.png",
            contentDescription = "${country.name} flag",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = country.name,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}