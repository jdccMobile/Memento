package com.jdccmobile.memento.data.model

import com.jdccmobile.memento.data.room.QuoteEntity

data class QuotesModel(
    val quote: String = "",
    val author: String = "",
)

fun QuoteEntity.toModel() = QuotesModel(quote, author)