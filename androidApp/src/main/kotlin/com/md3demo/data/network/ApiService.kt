package com.md3demo.data.network

import com.md3demo.data.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * API service interface for all network operations
 */
interface ApiService {
    suspend fun getProducts(): List<Product>
    suspend fun getProduct(id: Int): Product
    suspend fun getUsers(): List<User>
    suspend fun getUser(id: Int): User
    suspend fun getPosts(): List<Post>
    suspend fun searchGitHubUsers(query: String): GitHubSearchResponse
}

/**
 * Implementation of API service using Ktor client
 */
class ApiServiceImpl(private val httpClient: HttpClient) : ApiService {
    
    override suspend fun getProducts(): List<Product> {
        return httpClient.get("https://fakestoreapi.com/products").body()
    }
    
    override suspend fun getProduct(id: Int): Product {
        return httpClient.get("https://fakestoreapi.com/products/$id").body()
    }
    
    override suspend fun getUsers(): List<User> {
        return httpClient.get("https://jsonplaceholder.typicode.com/users").body()
    }
    
    override suspend fun getUser(id: Int): User {
        return httpClient.get("https://jsonplaceholder.typicode.com/users/$id").body()
    }
    
    override suspend fun getPosts(): List<Post> {
        return httpClient.get("https://jsonplaceholder.typicode.com/posts").body()
    }
    
    override suspend fun searchGitHubUsers(query: String): GitHubSearchResponse {
        return httpClient.get("https://api.github.com/search/users") {
            parameter("q", query)
        }.body()
    }
}

/**
 * Factory for creating HTTP client instances
 */
object HttpClientFactory {
    fun create(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }
}