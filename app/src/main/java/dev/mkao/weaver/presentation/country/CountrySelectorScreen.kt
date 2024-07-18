package dev.mkao.weaver.presentation.country

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import dev.mkao.weaver.presentation.navigation.Screen
import dev.mkao.weaver.viewModels.SharedViewModel

@Composable
fun CountrySelectorScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel
) {
    val selectedCountry by sharedViewModel.selectedCountry.collectAsState()
    Log.d("CountrySelectorScreen", "Observed selected country: ${selectedCountry?.name}")


    selectedCountry?.let {
        CountrySelector(
            onCountrySelected = { country ->
                sharedViewModel.setSelectedCountry(country)
                Log.d("CountrySelectorNavigate", "Country selected: ${country.name}")
                navController.navigate(Screen.Categories.route) {
                    popUpTo(Screen.Categories.route) { inclusive = false }
                    launchSingleTop = true
                }
            },
            sharedViewModel = sharedViewModel,
            onDismiss = {
                navController.navigate(Screen.Categories.route) {
                    popUpTo(Screen.Categories.route) { inclusive = false }
                    launchSingleTop = true
                }
            }
        )
    }
}


