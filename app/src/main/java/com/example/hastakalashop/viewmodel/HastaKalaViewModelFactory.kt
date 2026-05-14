package com.example.hastakalashop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hastakalashop.data.SalesRepository

class HastaKalaViewModelFactory(
    private val repository: SalesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HastaKalaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HastaKalaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
