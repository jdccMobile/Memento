package com.jdccmobile.memento.domain.room

import android.app.Application
import com.jdccmobile.memento.R
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.data.model.toModel
import com.jdccmobile.memento.data.room.QuoteDao
import com.jdccmobile.memento.data.room.QuoteEntity
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val quoteDao: QuoteDao,
    private val application: Application
) {

    suspend operator fun invoke(): List<QuotesModel> {
        val response: List<QuoteEntity> = quoteDao.getAllFavQuotes()
        if (response.isNotEmpty()) {
            return response.map { it.toModel() }
        } else {
            return listOf(
                QuotesModel(
                    application.applicationContext.getString(R.string.no_saved_quotes),
                    "Marco Aurelio"
                )
            )
        }

    }
}