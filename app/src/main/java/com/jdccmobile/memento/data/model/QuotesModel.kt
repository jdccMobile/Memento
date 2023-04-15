package com.jdccmobile.memento.data.model

import com.jdccmobile.memento.data.room.QuoteEntity
import com.jdccmobile.memento.domain.model.QuoteItem

data class QuotesModel(
    val quote: String = "",
    val author: String = "",
)

fun QuoteEntity.toModel() = QuotesModel(quote, author)