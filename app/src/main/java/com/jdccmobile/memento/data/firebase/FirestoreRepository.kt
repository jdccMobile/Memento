package com.jdccmobile.memento.data.firebase

import com.jdccmobile.memento.data.model.QuotesModel

interface FirestoreRepository {

    suspend fun getRandomQuote() : QuotesModel?
}