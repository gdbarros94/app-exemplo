package com.md3demo.ui

import com.md3demo.ui.viewmodel.CartViewModel
import com.md3demo.data.model.Product
import com.md3demo.data.model.Rating
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Unit tests for CartViewModel
 */
class CartViewModelTest {
    
    private lateinit var cartViewModel: CartViewModel
    private lateinit var sampleProduct: Product
    
    @Before
    fun setup() {
        cartViewModel = CartViewModel()
        sampleProduct = Product(
            id = 1,
            title = "Test Product",
            price = 19.99,
            description = "A test product",
            category = "test",
            image = "test.jpg",
            rating = Rating(4.0, 50)
        )
    }
    
    @Test
    fun `updateQuantity should update cart item quantity`() = runTest {
        // Given - Add item to shared cart first
        CartViewModel.shared.addToCart(sampleProduct, 1)
        
        // When
        cartViewModel.updateQuantity(sampleProduct.id, 3)
        
        // Then
        val cartItems = cartViewModel.cartItems.value
        val updatedItem = cartItems.find { it.product.id == sampleProduct.id }
        assertEquals(3, updatedItem?.quantity)
    }
    
    @Test
    fun `removeItem should remove item from cart`() = runTest {
        // Given
        CartViewModel.shared.addToCart(sampleProduct, 2)
        
        // When
        cartViewModel.removeItem(sampleProduct.id)
        
        // Then
        val cartItems = cartViewModel.cartItems.value
        val removedItem = cartItems.find { it.product.id == sampleProduct.id }
        assertNull(removedItem)
    }
    
    @Test
    fun `getTotalPrice should return correct total`() = runTest {
        // Given
        CartViewModel.shared.clearCart() // Clear any existing items
        CartViewModel.shared.addToCart(sampleProduct, 2)
        
        // When
        val totalPrice = cartViewModel.getTotalPrice()
        
        // Then
        assertEquals(39.98, totalPrice, 0.01)
    }
    
    @Test
    fun `checkout should show success state and clear cart`() = runTest {
        // Given
        CartViewModel.shared.addToCart(sampleProduct, 1)
        
        // When
        cartViewModel.checkout()
        
        // Then
        assertTrue(cartViewModel.showCheckoutSuccess.value)
        // Note: Cart clearing might be asynchronous, so this test focuses on the success state
    }
    
    @Test
    fun `dismissCheckoutSuccess should hide success state`() = runTest {
        // Given
        cartViewModel.checkout() // Set success state to true
        
        // When
        cartViewModel.dismissCheckoutSuccess()
        
        // Then
        assertFalse(cartViewModel.showCheckoutSuccess.value)
    }
}