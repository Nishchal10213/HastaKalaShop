package com.example.hastakalashop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hastakalashop.data.Product
import com.example.hastakalashop.data.SalesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HastaKalaViewModel(
    private val repository: SalesRepository
) : ViewModel() {
    private val screenState = MutableStateFlow(HastaKalaUiState())

    val uiState: StateFlow<HastaKalaUiState> = combine(
        screenState,
        repository.products,
        repository.lowStockProducts,
        repository.sales,
        repository.dashboard
    ) { state, products, lowStock, sales, dashboard ->
        state.copy(
            products = products,
            lowStockProducts = lowStock,
            sales = sales,
            dashboardStats = dashboard
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HastaKalaUiState()
    )

    init {
        viewModelScope.launch {
            repository.seedSampleProductsIfNeeded()
        }
    }

    fun saveSale(product: Product?, quantityText: String, priceText: String) {
        val selectedProduct = product ?: return showMessage("Select a product first")
        val quantity = quantityText.toIntOrNull()
        val price = priceText.toDoubleOrNull()

        if (quantity == null || quantity <= 0) {
            showMessage("Enter a valid quantity")
            return
        }
        if (price == null || price <= 0.0) {
            showMessage("Enter a valid price")
            return
        }
        if (quantity > selectedProduct.stockQuantity) {
            showMessage("Only ${selectedProduct.stockQuantity} items in stock")
            return
        }

        viewModelScope.launch {
            repository.saveSale(selectedProduct, quantity, price)
            showMessage("Sale saved")
        }
    }

    fun selectHistoryFilter(filter: HistoryFilter) {
        screenState.update { it.copy(selectedHistoryFilter = filter) }
    }

    fun clearMessage() {
        screenState.update { it.copy(message = null) }
    }

    private fun showMessage(message: String) {
        screenState.update { it.copy(message = message) }
    }
}
