package com.anthony.revolut.data


/**
 * Created by Anthony Koueik on 12/5/2019.
 * KOA
 * anthony.koueik@gmail.com
 *
 * wrapping my responses in one data resource class
 */
class DataResource<T> private constructor(val status: Status, val data: T?, val message: String?) {
    companion object {

        fun <T> success(data: T): DataResource<T> {
            return DataResource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): DataResource<T> {
            return DataResource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): DataResource<T> {
            return DataResource(Status.LOADING, data, null)
        }
    }
}