package com.jdccmobile.memento.data.model

import androidx.annotation.Keep
import com.jdccmobile.memento.data.room.QuoteEntity

@Keep
data class QuotesModel(
    val quote: String = "",
    val author: String = "",
)

fun QuoteEntity.toModel() = QuotesModel(quote, author)