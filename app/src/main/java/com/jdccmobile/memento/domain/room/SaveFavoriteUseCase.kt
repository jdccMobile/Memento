package com.jdccmobile.memento.domain.room

import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.data.room.QuoteDao
import com.jdccmobile.memento.data.room.toRoom
import javax.inject.Inject

class SaveFavoriteUseCase @Inject constructor(
    private val quoteDao: QuoteDao
) {

    suspend operator fun invoke(quote: QuotesModel) { quoteDao.insertFavQuote(quote.toRoom()) }
}