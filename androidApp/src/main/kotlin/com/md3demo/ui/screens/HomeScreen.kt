package com.md3demo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.md3demo.data.model.Product
import com.md3demo.ui.components.EmptyState
import com.md3demo.ui.components.ErrorState
import com.md3demo.ui.components.LoadingState
import com.md3demo.ui.components.ProductCard
import com.md3demo.ui.theme.Dimens
import com.md3demo.ui.viewmodel.CartViewModel
import com.md3demo.ui.viewmodel.HomeViewModel
import com.md3demo.util.UiState

/**
 * Home screen displaying list of products
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProductClick: (Int) -> Unit,
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    val productsState by homeViewModel.products.collectAsStateWithLifecycle()
    val searchQuery by homeViewModel.searchQuery.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Column(modifier = modifier.fillMaxSize()) {
        // Search Bar
        SearchBar(
            query = searchQuery,
            onQueryChange = homeViewModel::updateSearchQuery,
            onSearch = { query ->
                keyboardController?.hide()
                if (query.isNotBlank()) {
                    onSearchClick(query)
                }
            },
            active = false,
            onActiveChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.PaddingMedium),
            placeholder = {
                Text("Search products...")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        ) { }
        
        // Products List
        when (productsState) {
            is UiState.Loading -> {
                LoadingState(
                    modifier = Modifier.weight(1f),
                    message = "Loading products..."
                )
            }
            
            is UiState.Success -> {
                val products = productsState.data
                if (products.isEmpty()) {
                    EmptyState(
                        message = "No products available at the moment.",
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(Dimens.PaddingMedium),
                        verticalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium)
                    ) {
                        items(products) { product ->
                            ProductCard(
                                product = product,
                                onProductClick = { onProductClick(it.id) },
                                onAddToCart = { addedProduct ->
                                    CartViewModel.shared.addToCart(addedProduct)
                                }
                            )
                        }
                    }
                }
            }
            
            is UiState.Error -> {
                ErrorState(
                    message = productsState.message,
                    onRetry = homeViewModel::retry,
                    modifier = Modifier.weight(1f)
                )
            }
            
            is UiState.Empty -> {
                EmptyState(
                    message = "No products found.",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}