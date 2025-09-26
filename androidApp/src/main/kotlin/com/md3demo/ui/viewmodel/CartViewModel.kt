package com.md3demo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md3demo.data.model.CartItem
import com.md3demo.data.repository.CartManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for Cart screen
 */
class CartViewModel : ViewModel() {
    private val cartManager = CartManager()
    
    val cartItems: StateFlow<List<CartItem>> = cartManager.cartItems
    
    private val _showCheckoutSuccess = MutableStateFlow(false)
    val showCheckoutSuccess: StateFlow<Boolean> = _showCheckoutSuccess.asStateFlow()
    
    fun updateQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            cartManager.updateQuantity(productId, quantity)
        }
    }
    
    fun removeItem(productId: Int) {
        viewModelScope.launch {
            cartManager.removeFromCart(productId)
        }
    }
    
    fun getTotalPrice(): Double {
        return cartManager.getTotalPrice()
    }
    
    fun getTotalItems(): Int {
        return cartManager.getTotalItems()
    }
    
    fun checkout() {
        viewModelScope.launch {
            // Mock checkout process
            _showCheckoutSuccess.value = true
            // Clear cart after successful checkout
            cartManager.clearCart()
        }
    }
    
    fun dismissCheckoutSuccess() {
        _showCheckoutSuccess.value = false
    }
    
    companion object {
        // Shared instance for demo purposes
        val shared = CartManager()
    }
}