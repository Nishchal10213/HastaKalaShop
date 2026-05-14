package com.example.hastakalashop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hastakalashop.components.ColorfulScreen
import com.example.hastakalashop.components.HeroHeader
import com.example.hastakalashop.components.IncomeBarChart
import com.example.hastakalashop.components.ProductPieChart
import com.example.hastakalashop.components.StatCard
import com.example.hastakalashop.components.asRupees
import com.example.hastakalashop.viewmodel.HastaKalaUiState

@Composable
fun DashboardScreen(uiState: HastaKalaUiState) {
    val stats = uiState.dashboardStats
    ColorfulScreen {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroHeader(
                    title = "HastaKalaShop",
                    subtitle = "Colorful sales insights for handmade products",
                    accent = MaterialTheme.colorScheme.primary
                )
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    StatCard(
                        title = "Total Income",
                        value = stats.totalIncome.asRupees(),
                        supportingText = "${stats.totalSales} saved sales",
                        icon = Icons.Outlined.AttachMoney,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        iconTint = Color(0xFF8A5700),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Best Seller",
                        value = stats.bestSellingProduct,
                        supportingText = "${stats.totalItemsSold} items sold",
                        icon = Icons.Outlined.Star,
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        iconTint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    StatCard(
                        title = "Weekly Income",
                        value = stats.weeklyIncome.asRupees(),
                        icon = Icons.Outlined.TrendingUp,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        iconTint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Low Stock",
                        value = stats.lowStockCount.toString(),
                        supportingText = "Needs restock",
                        icon = Icons.Outlined.Inventory,
                        containerColor = Color(0xFFFFE0B2),
                        iconTint = Color(0xFFE65100),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                ProductPieChart(sales = uiState.sales, modifier = Modifier.fillMaxWidth())
            }
            item {
                IncomeBarChart(sales = uiState.sales, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
            }
        }
    }
}
