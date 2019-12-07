package com.anthony.revolut.utils

import android.content.Context
import com.anthony.revolut.MyApplication
import com.anthony.revolut.R
import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


/**
 * Created by Anthony Koueik on 12/7/2019.
 * KOA
 * anthony.koueik@gmail.com
 */

fun getCustomErrorMessage(error: Throwable): String {

    return if (error is SocketTimeoutException) {
        "We couldn\\’t capture your request in time. Please try again."
    } else if (error is MalformedJsonException) {
        "We hit an error. Try again later"
    } else if (error is IOException) {
        "You are not connected to a wifi or cellular data network. Please connect and try again"
    } else if (error is HttpException) {
        error.response().message()
    } else {
        "Something went wrong!"
    }
}