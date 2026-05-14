package com.example.hastakalashop.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hastakalashop.ui.AnalyticsScreen
import com.example.hastakalashop.ui.DashboardScreen
import com.example.hastakalashop.ui.QuickBillingScreen
import com.example.hastakalashop.ui.SalesHistoryScreen
import com.example.hastakalashop.ui.StockAlertScreen
import com.example.hastakalashop.viewmodel.HastaKalaViewModel

@Composable
fun HastaKalaNavigation(viewModel: HastaKalaViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                appRoutes.forEach { route ->
                    val selected = currentDestination?.hierarchy?.any { it.route == route.path } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(route.path) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(route.icon, contentDescription = route.label) },
                        label = { Text(route.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Route.Dashboard.path,
            modifier = Modifier.padding(padding)
        ) {
            composable(Route.Dashboard.path) {
                DashboardScreen(uiState = uiState)
            }
            composable(Route.Billing.path) {
                QuickBillingScreen(
                    uiState = uiState,
                    onSaveSale = viewModel::saveSale,
                    onMessageShown = viewModel::clearMessage
                )
            }
            composable(Route.Stock.path) {
                StockAlertScreen(uiState = uiState)
            }
            composable(Route.History.path) {
                SalesHistoryScreen(
                    uiState = uiState,
                    onFilterSelected = viewModel::selectHistoryFilter
                )
            }
            composable(Route.Analytics.path) {
                AnalyticsScreen(uiState = uiState)
            }
        }
    }
}
