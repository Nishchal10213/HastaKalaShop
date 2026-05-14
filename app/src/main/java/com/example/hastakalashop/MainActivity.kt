package com.example.hastakalashop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hastakalashop.database.HastaKalaDatabase
import com.example.hastakalashop.data.SalesRepository
import com.example.hastakalashop.navigation.HastaKalaNavigation
import com.example.hastakalashop.ui.theme.HastaKalaShopTheme
import com.example.hastakalashop.viewmodel.HastaKalaViewModel
import com.example.hastakalashop.viewmodel.HastaKalaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = HastaKalaDatabase.getDatabase(applicationContext)
        val repository = SalesRepository(database.productDao(), database.saleDao())
        val factory = HastaKalaViewModelFactory(repository)

        setContent {
            val viewModel: HastaKalaViewModel = viewModel(factory = factory)
            HastaKalaShopTheme {
                HastaKalaNavigation(viewModel = viewModel)
            }
        }
    }
}
