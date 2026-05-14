package com.example.hastakalashop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.example.hastakalashop.components.ColorfulScreen
import com.example.hastakalashop.components.HeroHeader
import com.example.hastakalashop.components.asRupees
import com.example.hastakalashop.data.Product
import com.example.hastakalashop.viewmodel.HastaKalaUiState

@Composable
fun QuickBillingScreen(
    uiState: HastaKalaUiState,
    onSaveSale: (Product?, String, String) -> Unit,
    onMessageShown: () -> Unit
) {
    var selectedProduct by remember(uiState.products) { mutableStateOf(uiState.products.firstOrNull()) }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            onMessageShown()
        }
    }

    ColorfulScreen {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroHeader(
                    title = "Quick Billing",
                    subtitle = "Save a sale and update stock in seconds",
                    accent = MaterialTheme.colorScheme.tertiary
                )
            }
            item {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                    ProductPicker(
                        products = uiState.products,
                        selectedProduct = selectedProduct,
                        onProductSelected = { selectedProduct = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = selectedProduct?.color.orEmpty(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Color / Design") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = quantity,
                            onValueChange = { quantity = it.filter(Char::isDigit) },
                            label = { Text("Quantity") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = price,
                            onValueChange = { price = it },
                            label = { Text("Price") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Text(
                        text = "Available stock: ${selectedProduct?.stockQuantity ?: 0}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Bill total: ${((quantity.toIntOrNull() ?: 0) * (price.toDoubleOrNull() ?: 0.0)).asRupees()}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Button(
                        onClick = {
                            onSaveSale(selectedProduct, quantity, price)
                            quantity = ""
                            price = ""
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Outlined.Save, contentDescription = "Save sale")
                        Text("Save Sale")
                    }
                    }
                }
            }
            item {
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductPicker(
    products: List<Product>,
    selectedProduct: Product?,
    onProductSelected: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedProduct?.name.orEmpty(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Product Name") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            products.forEach { product ->
                DropdownMenuItem(
                    text = { Text("${product.name} - ${product.color}") },
                    onClick = {
                        onProductSelected(product)
                        expanded = false
                    }
                )
            }
        }
    }
}
