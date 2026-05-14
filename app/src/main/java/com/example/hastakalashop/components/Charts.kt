package com.example.hastakalashop.components

import android.graphics.Color
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.hastakalashop.data.Sale
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

private val chartColors = listOf(
    Color.rgb(47, 111, 98),
    Color.rgb(230, 126, 34),
    Color.rgb(66, 133, 244),
    Color.rgb(185, 71, 93),
    Color.rgb(118, 96, 184)
)

@Composable
fun ProductPieChart(sales: List<Sale>, modifier: Modifier = Modifier) {
    ChartCard(title = "Product Share", modifier = modifier) {
        AndroidView(
            modifier = Modifier.height(260.dp),
            factory = { context ->
                PieChart(context).apply {
                    description.isEnabled = false
                    legend.isEnabled = true
                    setUsePercentValues(true)
                    setDrawEntryLabels(false)
                    holeRadius = 54f
                    transparentCircleRadius = 58f
                }
            },
            update = { chart ->
                val entries = sales.productTotals().ifEmpty {
                    mapOf("Scarves" to 8f, "Diya Sets" to 5f, "Baskets" to 3f, "Totes" to 4f)
                }.map { PieEntry(it.value, it.key) }

                chart.data = PieData(PieDataSet(entries, "").apply {
                    setColors(chartColors)
                    valueTextSize = 12f
                    valueTextColor = Color.WHITE
                })
                chart.invalidate()
            }
        )
    }
}

@Composable
fun IncomeBarChart(sales: List<Sale>, modifier: Modifier = Modifier) {
    ChartCard(title = "Income by Product", modifier = modifier) {
        AndroidView(
            modifier = Modifier.height(260.dp),
            factory = { context ->
                BarChart(context).apply {
                    description.isEnabled = false
                    legend.isEnabled = false
                    axisRight.isEnabled = false
                    xAxis.setDrawGridLines(false)
                    axisLeft.axisMinimum = 0f
                }
            },
            update = { chart ->
                val totals = sales.incomeTotals().ifEmpty {
                    mapOf("Scarf" to 3200f, "Diya" to 1800f, "Basket" to 1400f, "Tote" to 2400f)
                }
                val entries = totals.values.mapIndexed { index, value -> BarEntry(index.toFloat(), value) }
                chart.data = BarData(BarDataSet(entries, "Income").apply {
                    setColors(chartColors)
                    valueTextSize = 11f
                })
                chart.xAxis.labelCount = totals.size
                chart.invalidate()
            }
        )
    }
}

@Composable
private fun ChartCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(start = 18.dp, top = 18.dp, end = 18.dp),
            style = MaterialTheme.typography.titleMedium
        )
        content()
    }
}

private fun List<Sale>.productTotals(): Map<String, Float> =
    groupBy { it.productName }.mapValues { entry -> entry.value.sumOf { it.quantity }.toFloat() }

private fun List<Sale>.incomeTotals(): Map<String, Float> =
    groupBy { it.productName }.mapValues { entry -> entry.value.sumOf { it.totalAmount }.toFloat() }
