package com.md3demo.data.repository

import com.md3demo.data.model.*
import com.md3demo.data.network.ApiService
import com.md3demo.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repository for product-related data operations
 */
interface ProductRepository {
    fun getProducts(): Flow<NetworkResult<List<Product>>>
    fun getProduct(id: Int): Flow<NetworkResult<Product>>
}

class ProductRepositoryImpl(private val apiService: ApiService) : ProductRepository {
    
    // Simple in-memory cache
    private var productsCache: List<Product>? = null
    private val productCache = mutableMapOf<Int, Product>()
    
    override fun getProducts(): Flow<NetworkResult<List<Product>>> = flow {
        try {
            emit(NetworkResult.Loading)
            
            // Return cached data if available
            productsCache?.let {
                emit(NetworkResult.Success(it))
                return@flow
            }
            
            val products = apiService.getProducts()
            productsCache = products
            
            // Cache individual products
            products.forEach { product ->
                productCache[product.id] = product
            }
            
            emit(NetworkResult.Success(products))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Unknown error"))
        }
    }
    
    override fun getProduct(id: Int): Flow<NetworkResult<Product>> = flow {
        try {
            emit(NetworkResult.Loading)
            
            // Return cached product if available
            productCache[id]?.let {
                emit(NetworkResult.Success(it))
                return@flow
            }
            
            val product = apiService.getProduct(id)
            productCache[id] = product
            emit(NetworkResult.Success(product))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Unknown error"))
        }
    }
}

/**
 * Repository for user-related data operations
 */
interface UserRepository {
    fun getUsers(): Flow<NetworkResult<List<User>>>
    fun getUser(id: Int): Flow<NetworkResult<User>>
    fun getPosts(): Flow<NetworkResult<List<Post>>>
}

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {
    
    private var usersCache: List<User>? = null
    private var postsCache: List<Post>? = null
    
    override fun getUsers(): Flow<NetworkResult<List<User>>> = flow {
        try {
            emit(NetworkResult.Loading)
            
            usersCache?.let {
                emit(NetworkResult.Success(it))
                return@flow
            }
            
            val users = apiService.getUsers()
            usersCache = users
            emit(NetworkResult.Success(users))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Unknown error"))
        }
    }
    
    override fun getUser(id: Int): Flow<NetworkResult<User>> = flow {
        try {
            emit(NetworkResult.Loading)
            val user = apiService.getUser(id)
            emit(NetworkResult.Success(user))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Unknown error"))
        }
    }
    
    override fun getPosts(): Flow<NetworkResult<List<Post>>> = flow {
        try {
            emit(NetworkResult.Loading)
            
            postsCache?.let {
                emit(NetworkResult.Success(it))
                return@flow
            }
            
            val posts = apiService.getPosts()
            postsCache = posts
            emit(NetworkResult.Success(posts))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Unknown error"))
        }
    }
}

/**
 * Repository for search operations
 */
interface SearchRepository {
    fun searchUsers(query: String): Flow<NetworkResult<GitHubSearchResponse>>
}

class SearchRepositoryImpl(private val apiService: ApiService) : SearchRepository {
    
    override fun searchUsers(query: String): Flow<NetworkResult<GitHubSearchResponse>> = flow {
        try {
            emit(NetworkResult.Loading)
            val response = apiService.searchGitHubUsers(query)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Search failed"))
        }
    }
}