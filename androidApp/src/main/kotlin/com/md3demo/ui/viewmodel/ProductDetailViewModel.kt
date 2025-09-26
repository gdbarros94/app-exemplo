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
 * ViewModel for Product Detail screen
 */
class ProductDetailViewModel : ViewModel() {
    private val apiService = ApiServiceImpl(HttpClientFactory.create())
    private val productRepository = ProductRepositoryImpl(apiService)
    
    private val _product = MutableStateFlow<UiState<Product>>(UiState.Loading)
    val product: StateFlow<UiState<Product>> = _product.asStateFlow()
    
    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()
    
    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            productRepository.getProduct(productId).collect { networkResult ->
                _product.value = networkResult.toUiState()
            }
        }
    }
    
    fun showActionBottomSheet() {
        _showBottomSheet.value = true
    }
    
    fun hideBottomSheet() {
        _showBottomSheet.value = false
    }
    
    fun retry(productId: Int) {
        _product.value = UiState.Loading
        loadProduct(productId)
    }
}