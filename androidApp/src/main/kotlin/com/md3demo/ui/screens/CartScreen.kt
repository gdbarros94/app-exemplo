package com.md3demo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.md3demo.data.model.CartItem
import com.md3demo.ui.components.EmptyState
import com.md3demo.ui.theme.Dimens
import com.md3demo.ui.viewmodel.CartViewModel

/**
 * Cart screen showing cart items and checkout functionality
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = viewModel()
) {
    val cartItems by CartViewModel.shared.cartItems.collectAsStateWithLifecycle()
    val showCheckoutSuccess by cartViewModel.showCheckoutSuccess.collectAsStateWithLifecycle()
    
    // Show success snackbar
    if (showCheckoutSuccess) {
        LaunchedEffect(showCheckoutSuccess) {
            cartViewModel.dismissCheckoutSuccess()
        }
    }
    
    if (cartItems.isEmpty()) {
        EmptyState(
            message = "Your cart is empty. Add some products to get started!",
            modifier = modifier.fillMaxSize()
        )
    } else {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            // Cart Items List
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(Dimens.PaddingMedium),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium)
            ) {
                items(cartItems) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onQuantityChange = { quantity ->
                            cartViewModel.updateQuantity(cartItem.product.id, quantity)
                        },
                        onRemove = {
                            cartViewModel.removeItem(cartItem.product.id)
                        }
                    )
                }
            }
            
            // Checkout Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.PaddingMedium),
                shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = Dimens.ElevationMedium
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.PaddingLarge)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Items:",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "${CartViewModel.shared.getTotalItems()}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Price:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "$${String.format("%.2f", CartViewModel.shared.getTotalPrice())}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    Button(
                        onClick = {
                            cartViewModel.checkout()
                            onCheckout()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.MinTouchTarget)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                        Text("Checkout")
                    }
                }
            }
        }
    }
}

@Composable
private fun CartItemCard(
    cartItem: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.CornerRadiusMedium),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.ElevationSmall
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cartItem.product.image)
                    .crossfade(true)
                    .build(),
                contentDescription = cartItem.product.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(Dimens.CornerRadiusSmall)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
            
            // Product Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.product.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                Text(
                    text = "$${cartItem.product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                
                // Quantity Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledIconButton(
                        onClick = { 
                            if (cartItem.quantity > 1) {
                                onQuantityChange(cartItem.quantity - 1)
                            } else {
                                onRemove()
                            }
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (cartItem.quantity > 1) Icons.Default.Remove else Icons.Default.Delete,
                            contentDescription = if (cartItem.quantity > 1) "Decrease quantity" else "Remove item",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    
                    Text(
                        text = "${cartItem.quantity}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = Dimens.SpaceMedium),
                        textAlign = TextAlign.Center
                    )
                    
                    FilledIconButton(
                        onClick = { onQuantityChange(cartItem.quantity + 1) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase quantity",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}