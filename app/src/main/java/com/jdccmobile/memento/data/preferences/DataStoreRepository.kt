package com.jdccmobile.memento.data.preferences

import com.jdccmobile.memento.data.model.QuotesModel

interface DataStoreRepository {

    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?

    suspend fun putInt(key: String, value: Int)
    suspend fun getInt(key: String): Int?

    suspend fun putBool(key: String, value: Boolean)
    suspend fun getBool(key: String): Boolean?


}