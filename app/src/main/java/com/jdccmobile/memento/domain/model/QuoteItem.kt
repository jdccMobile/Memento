package com.jdccmobile.memento.domain.model

import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.data.room.QuoteEntity

data class QuoteItem(
    val quote: String = "",
    val author: String = "",
)

fun QuoteEntity.toDomain() = QuoteItem(quote, author)