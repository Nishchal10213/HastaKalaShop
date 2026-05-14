package com.example.hastakalashop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hastakalashop.components.ColorfulScreen
import com.example.hastakalashop.components.HeroHeader
import com.example.hastakalashop.components.StatCard
import com.example.hastakalashop.components.asRupees
import com.example.hastakalashop.viewmodel.HastaKalaUiState

@Composable
fun AnalyticsScreen(uiState: HastaKalaUiState) {
    val stats = uiState.dashboardStats
    val artisanImpact = (stats.totalItemsSold * 1.5).toInt()

    ColorfulScreen {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroHeader(
                    title = "Analytics",
                    subtitle = "Income, product trends, and impact statistics",
                    accent = MaterialTheme.colorScheme.tertiary
                )
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    StatCard(
                        title = "Total Income",
                        value = stats.totalIncome.asRupees(),
                        icon = Icons.Outlined.AttachMoney,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        iconTint = Color(0xFF8A5700),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Top Product",
                        value = stats.bestSellingProduct,
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
                        title = "Average Bill",
                        value = stats.averageOrderValue.asRupees(),
                        icon = Icons.Outlined.ShoppingBag,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        iconTint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Impact Score",
                        value = "$artisanImpact",
                        supportingText = "Based on sales volume",
                        icon = Icons.Outlined.Groups,
                        containerColor = Color(0xFFFFE0B2),
                        iconTint = Color(0xFFE65100),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Impact Notes", style = MaterialTheme.typography.titleLarge)
                    Text("Every saved sale improves demand visibility, helps plan restocking, and gives artisans clearer income patterns.")
                    Text("Monthly income tracked: ${stats.monthlyIncome.asRupees()}")
                    Text("Products needing restock: ${stats.lowStockCount}")
                }
            }
        }
    }
}
