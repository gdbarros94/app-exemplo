package com.md3demo.data.model

import kotlinx.serialization.Serializable

/**
 * Data model representing a product from the FakeStore API
 */
@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating? = null
)

@Serializable
data class Rating(
    val rate: Double,
    val count: Int
)

/**
 * Local cart item with quantity
 */
@Serializable
data class CartItem(
    val product: Product,
    val quantity: Int = 1
)

/**
 * User data model from JSONPlaceholder API
 */
@Serializable
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address? = null,
    val phone: String? = null,
    val website: String? = null,
    val company: Company? = null
)

@Serializable
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo? = null
)

@Serializable
data class Geo(
    val lat: String,
    val lng: String
)

@Serializable
data class Company(
    val name: String,
    val catchPhrase: String? = null,
    val bs: String? = null
)

/**
 * Post data model from JSONPlaceholder API
 */
@Serializable
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

/**
 * GitHub search user response
 */
@Serializable
data class GitHubSearchResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<GitHubUser>
)

@Serializable
data class GitHubUser(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val type: String = "User"
)