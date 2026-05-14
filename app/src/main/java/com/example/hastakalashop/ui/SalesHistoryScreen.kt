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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hastakalashop.components.ColorfulScreen
import com.example.hastakalashop.components.HeroHeader
import com.example.hastakalashop.components.asRupees
import com.example.hastakalashop.components.asSaleDate
import com.example.hastakalashop.data.Sale
import com.example.hastakalashop.viewmodel.HastaKalaUiState
import com.example.hastakalashop.viewmodel.HistoryFilter

@Composable
fun SalesHistoryScreen(
    uiState: HastaKalaUiState,
    onFilterSelected: (HistoryFilter) -> Unit
) {
    ColorfulScreen {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                HeroHeader(
                    title = "Sales History",
                    subtitle = "Review saved bills by week or month",
                    accent = MaterialTheme.colorScheme.primary
                )
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    HistoryFilter.entries.forEach { filter ->
                        FilterChip(
                            selected = uiState.selectedHistoryFilter == filter,
                            onClick = { onFilterSelected(filter) },
                            label = { Text(filter.label) }
                        )
                    }
                }
            }
            if (uiState.filteredSales.isEmpty()) {
                item {
                    Text("No sales found for this filter.", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                items(uiState.filteredSales, key = { it.id }) { sale ->
                    SaleHistoryItem(sale = sale)
                }
            }
        }
    }
}

@Composable
private fun SaleHistoryItem(sale: Sale) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(sale.productName, style = MaterialTheme.typography.titleMedium)
                Text(sale.totalAmount.asRupees(), style = MaterialTheme.typography.titleMedium)
            }
            Text("${sale.color} - Qty ${sale.quantity} - ${sale.price.asRupees()} each")
            Text(sale.timestamp.asSaleDate(), style = MaterialTheme.typography.bodySmall)
        }
    }
}
