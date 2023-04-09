package com.jdccmobile.memento.data.preferences

interface DataStoreRepository {

    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?

    suspend fun putInt(key: String, value: Int)
    suspend fun getInt(key: String): Int?


}