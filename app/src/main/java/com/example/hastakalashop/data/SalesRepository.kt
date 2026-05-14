package com.example.hastakalashop.data

import com.example.hastakalashop.database.ProductDao
import com.example.hastakalashop.database.SaleDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SalesRepository(
    private val productDao: ProductDao,
    private val saleDao: SaleDao
) {
    val products: Flow<List<Product>> = productDao.observeProducts()
    val sales: Flow<List<Sale>> = saleDao.observeSales()
    val lowStockProducts: Flow<List<Product>> = productDao.observeLowStockProducts()

    val dashboard: Flow<DashboardStats> = combine(products, sales) { products, sales ->
        val totalIncome = sales.sumOf { it.totalAmount }
        val totalItemsSold = sales.sumOf { it.quantity }
        val bestSeller = sales
            .groupBy { it.productName }
            .maxByOrNull { entry -> entry.value.sumOf { it.quantity } }
            ?.key ?: "No sales yet"

        DashboardStats(
            totalSales = sales.size,
            totalIncome = totalIncome,
            weeklyIncome = sales.inLastDays(7).sumOf { it.totalAmount },
            monthlyIncome = sales.inLastDays(30).sumOf { it.totalAmount },
            bestSellingProduct = bestSeller,
            totalItemsSold = totalItemsSold,
            averageOrderValue = if (sales.isEmpty()) 0.0 else totalIncome / sales.size,
            lowStockCount = products.count { it.stockQuantity <= LOW_STOCK_LIMIT }
        )
    }

    suspend fun seedSampleProductsIfNeeded() {
        if (productDao.productCount() > 0) return
        productDao.insertProducts(sampleProducts)
    }

    suspend fun saveSale(product: Product, quantity: Int, price: Double) {
        val safeQuantity = quantity.coerceAtLeast(1)
        val sale = Sale(
            productName = product.name,
            color = product.color,
            quantity = safeQuantity,
            price = price,
            totalAmount = safeQuantity * price
        )
        saleDao.insertSale(sale)
        productDao.updateProduct(product.copy(stockQuantity = (product.stockQuantity - safeQuantity).coerceAtLeast(0)))
    }

    private fun List<Sale>.inLastDays(days: Int): List<Sale> {
        val start = System.currentTimeMillis() - days * 24L * 60L * 60L * 1000L
        return filter { it.timestamp >= start }
    }

    companion object {
        const val LOW_STOCK_LIMIT = 5

        private val sampleProducts = listOf(
            Product(name = "Handwoven Scarf", color = "Indigo Block", stockQuantity = 14),
            Product(name = "Clay Diya Set", color = "Terracotta Gold", stockQuantity = 4),
            Product(name = "Bamboo Basket", color = "Natural Weave", stockQuantity = 9),
            Product(name = "Embroidered Tote", color = "Marigold Thread", stockQuantity = 3),
            Product(name = "Brass Bell", color = "Antique Finish", stockQuantity = 7),
            Product(name = "Madhubani Coaster", color = "Peacock Motif", stockQuantity = 12)
        )
    }
}

data class DashboardStats(
    val totalSales: Int = 0,
    val totalIncome: Double = 0.0,
    val weeklyIncome: Double = 0.0,
    val monthlyIncome: Double = 0.0,
    val bestSellingProduct: String = "No sales yet",
    val totalItemsSold: Int = 0,
    val averageOrderValue: Double = 0.0,
    val lowStockCount: Int = 0
)
