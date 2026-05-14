package com.example.hastakalashop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hastakalashop.components.ColorfulScreen
import com.example.hastakalashop.components.HeroHeader
import com.example.hastakalashop.data.SalesRepository
import com.example.hastakalashop.viewmodel.HastaKalaUiState

@Composable
fun StockAlertScreen(uiState: HastaKalaUiState) {
    ColorfulScreen {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                HeroHeader(
                    title = "Stock Alerts",
                    subtitle = "Products at or below ${SalesRepository.LOW_STOCK_LIMIT} units need attention",
                    accent = MaterialTheme.colorScheme.secondary
                )
            }
            if (uiState.lowStockProducts.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            text = "All products have healthy stock.",
                            modifier = Modifier.fillMaxWidth().padding(18.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            } else {
                items(uiState.lowStockProducts, key = { it.id }) { product ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (product.stockQuantity <= 3) {
                                MaterialTheme.colorScheme.errorContainer
                            } else {
                                MaterialTheme.colorScheme.tertiaryContainer
                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(product.name, style = MaterialTheme.typography.titleLarge)
                                Text(product.color, style = MaterialTheme.typography.bodyMedium)
                            }
                            AssistChip(
                                onClick = {},
                                label = { Text("${product.stockQuantity} left") },
                                leadingIcon = { Icon(Icons.Outlined.WarningAmber, contentDescription = "Low stock") }
                            )
                        }
                    }
                }
            }
        }
    }
}
