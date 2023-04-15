package com.jdccmobile.memento.domain.room

import android.util.Log
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.data.model.toModel
import com.jdccmobile.memento.data.room.QuoteDao
import com.jdccmobile.memento.data.room.QuoteEntity
import com.jdccmobile.memento.ui.views.SplashActivity
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val quoteDao: QuoteDao
) {

    suspend operator fun invoke(): List<QuotesModel>? {
        val response: List<QuoteEntity> = quoteDao.getAllFavQuotes()
        if (response.isNotEmpty()) {
            return response.map { it.toModel() }

        } else {
            val responseNull: List<QuotesModel> =
                listOf(QuotesModel("No ha citas favoritas guardadas", "aristotles"))
            return responseNull
        }

    }
}