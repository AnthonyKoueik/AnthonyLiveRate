package com.anthony.revolut.data


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 *
 * wrapping my responses in one data resource class
 */
sealed class Resource<out T>
data class Loading<T>(val data: T? = null) : Resource<T>() // if you don’t have paging, we don’t need data here
data class Success<T>(val data: T) : Resource<T>()
data class Error<T>(val message: String) : Resource<T>()
