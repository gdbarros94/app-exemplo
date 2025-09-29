package com.md3demo.data

import com.md3demo.data.model.CartItem
import com.md3demo.data.model.Product
import com.md3demo.data.model.Rating
import com.md3demo.data.repository.CartManager
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Unit tests for CartManager
 */
class CartManagerTest {
    
    private lateinit var cartManager: CartManager
    private lateinit var sampleProduct: Product
    
    @Before
    fun setup() {
        cartManager = CartManager()
        sampleProduct = Product(
            id = 1,
            title = "Test Product",
            price = 29.99,
            description = "A test product",
            category = "test",
            image = "test.jpg",
            rating = Rating(4.5, 100)
        )
    }
    
    @Test
    fun `addToCart should add new product to cart`() = runTest {
        // When
        cartManager.addToCart(sampleProduct, 2)
        
        // Then
        val cartItems = cartManager.cartItems.value
        assertEquals(1, cartItems.size)
        assertEquals(sampleProduct.id, cartItems[0].product.id)
        assertEquals(2, cartItems[0].quantity)
    }
    
    @Test
    fun `addToCart should update quantity for existing product`() = runTest {
        // Given
        cartManager.addToCart(sampleProduct, 1)
        
        // When
        cartManager.addToCart(sampleProduct, 2)
        
        // Then
        val cartItems = cartManager.cartItems.value
        assertEquals(1, cartItems.size)
        assertEquals(3, cartItems[0].quantity)
    }
    
    @Test
    fun `removeFromCart should remove product from cart`() = runTest {
        // Given
        cartManager.addToCart(sampleProduct)
        
        // When
        cartManager.removeFromCart(sampleProduct.id)
        
        // Then
        val cartItems = cartManager.cartItems.value
        assertTrue(cartItems.isEmpty())
    }
    
    @Test
    fun `updateQuantity should update product quantity`() = runTest {
        // Given
        cartManager.addToCart(sampleProduct, 1)
        
        // When
        cartManager.updateQuantity(sampleProduct.id, 5)
        
        // Then
        val cartItems = cartManager.cartItems.value
        assertEquals(5, cartItems[0].quantity)
    }
    
    @Test
    fun `updateQuantity with zero should remove item`() = runTest {
        // Given
        cartManager.addToCart(sampleProduct)
        
        // When
        cartManager.updateQuantity(sampleProduct.id, 0)
        
        // Then
        val cartItems = cartManager.cartItems.value
        assertTrue(cartItems.isEmpty())
    }
    
    @Test
    fun `getTotalPrice should calculate correct total`() = runTest {
        // Given
        cartManager.addToCart(sampleProduct, 2)
        
        // When
        val totalPrice = cartManager.getTotalPrice()
        
        // Then
        assertEquals(59.98, totalPrice, 0.01)
    }
    
    @Test
    fun `getTotalItems should count all items`() = runTest {
        // Given
        cartManager.addToCart(sampleProduct, 3)
        val product2 = sampleProduct.copy(id = 2)
        cartManager.addToCart(product2, 2)
        
        // When
        val totalItems = cartManager.getTotalItems()
        
        // Then
        assertEquals(5, totalItems)
    }
    
    @Test
    fun `clearCart should empty the cart`() = runTest {
        // Given
        cartManager.addToCart(sampleProduct, 3)
        
        // When
        cartManager.clearCart()
        
        // Then
        val cartItems = cartManager.cartItems.value
        assertTrue(cartItems.isEmpty())
        assertEquals(0.0, cartManager.getTotalPrice(), 0.01)
        assertEquals(0, cartManager.getTotalItems())
    }
}