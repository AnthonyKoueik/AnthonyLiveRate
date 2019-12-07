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

fun getCustomErrorMessage(error: Throwable, context: Context): String {

    return if (error is SocketTimeoutException) {
        context.getString(R.string.requestTimeOutError)
    } else if (error is MalformedJsonException) {
        context.getString(R.string.responseMalformedJson)
    } else if (error is IOException) {
        context.getString(R.string.networkError)
    } else if (error is HttpException) {
        error.response().message()
    } else {
        context.getString(R.string.unknownError)
    }
}