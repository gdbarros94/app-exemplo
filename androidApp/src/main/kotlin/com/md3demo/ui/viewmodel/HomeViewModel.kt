package com.md3demo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md3demo.data.model.Product
import com.md3demo.data.network.HttpClientFactory
import com.md3demo.data.network.ApiServiceImpl
import com.md3demo.data.repository.ProductRepositoryImpl
import com.md3demo.util.UiState
import com.md3demo.util.toUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home screen that displays products
 */
class HomeViewModel : ViewModel() {
    private val apiService = ApiServiceImpl(HttpClientFactory.create())
    private val productRepository = ProductRepositoryImpl(apiService)
    
    private val _products = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val products: StateFlow<UiState<List<Product>>> = _products.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    init {
        loadProducts()
    }
    
    fun loadProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collect { networkResult ->
                _products.value = networkResult.toUiState()
            }
        }
    }
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun refreshProducts() {
        _products.value = UiState.Loading
        loadProducts()
    }
    
    fun retry() {
        loadProducts()
    }
}