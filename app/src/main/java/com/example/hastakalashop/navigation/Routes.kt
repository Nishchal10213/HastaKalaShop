package com.example.hastakalashop.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(
    val path: String,
    val label: String,
    val icon: ImageVector
) {
    data object Dashboard : Route("dashboard", "Dashboard", Icons.Outlined.Dashboard)
    data object Billing : Route("billing", "Billing", Icons.Outlined.AddShoppingCart)
    data object Stock : Route("stock", "Stock", Icons.Outlined.Inventory)
    data object History : Route("history", "History", Icons.Outlined.History)
    data object Analytics : Route("analytics", "Analytics", Icons.Outlined.Analytics)
}

val appRoutes = listOf(
    Route.Dashboard,
    Route.Billing,
    Route.Stock,
    Route.History,
    Route.Analytics
)
