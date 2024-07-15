package dev.mkao.weaver.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.mkao.weaver.domain.model.Country

@Composable
fun CountrySelectionScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf(getCountries().firstOrNull()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSurface)
    ) {
        TopBar(selectedCountry = selectedCountry)
        SearchBar(searchQuery = searchQuery, onSearchQueryChange = { searchQuery = it })
        CountryList(countries = getCountries(), onCountrySelected = { selectedCountry = it })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(selectedCountry: Country?) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(top = 30.dp),
        title = { Text("Select Country", color = Color(0xFF4CAF50)) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            IconButton(onClick = { /* Handle back navigation */ }) {
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

@Composable
fun CountryList(countries: List<Country>, onCountrySelected: (Country) -> Unit) {
    LazyColumn {
        items(countries) { country ->
            CountryItem(country = country, onCountrySelected = onCountrySelected)
        }
    }
}


fun getCountries(): List<Country> {
    return listOf(
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
        Country("US", "United States"),
        Country("VE", "Venezuela"),
        Country("ZA", "South Africa")
    )
}

@Composable
fun CountryItem(country: Country, onCountrySelected: (Country) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://flagsapi.com/${country.code}/flat/64.png",
            contentDescription = "${country.name} flag",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = country.name, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        if (country.isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color(0xFF4CAF50)
            )
        }
    }
}
@Preview
@Composable
fun CountryPreview(){
    CountrySelectionScreen()
}