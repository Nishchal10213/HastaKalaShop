package com.example.hastakalashop.viewmodel

import com.example.hastakalashop.data.DashboardStats
import com.example.hastakalashop.data.Product
import com.example.hastakalashop.data.Sale

data class HastaKalaUiState(
    val products: List<Product> = emptyList(),
    val lowStockProducts: List<Product> = emptyList(),
    val sales: List<Sale> = emptyList(),
    val dashboardStats: DashboardStats = DashboardStats(),
    val selectedHistoryFilter: HistoryFilter = HistoryFilter.All,
    val message: String? = null
) {
    val filteredSales: List<Sale>
        get() {
            val now = System.currentTimeMillis()
            val start = when (selectedHistoryFilter) {
                HistoryFilter.All -> Long.MIN_VALUE
                HistoryFilter.Week -> now - 7 * 24L * 60L * 60L * 1000L
                HistoryFilter.Month -> now - 30 * 24L * 60L * 60L * 1000L
            }
            return sales.filter { it.timestamp >= start }
        }
}

enum class HistoryFilter(val label: String) {
    All("All"),
    Week("Week"),
    Month("Month")
}
