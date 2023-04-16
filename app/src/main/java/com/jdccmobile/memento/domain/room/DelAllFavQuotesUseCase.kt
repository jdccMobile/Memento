package com.jdccmobile.memento.domain.room

import com.jdccmobile.memento.data.room.QuoteDao
import javax.inject.Inject

class DelAllFavQuotesUseCase @Inject constructor(
    private val quoteDao: QuoteDao
) {
    suspend operator fun invoke(){ quoteDao.delAllFavQuotes() }
}