package com.jdccmobile.memento.domain

import android.util.Log
import com.jdccmobile.memento.data.firebase.FirestoreRepository
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.ui.views.SplashActivity
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) {

    suspend operator fun invoke():QuotesModel? {
        Log.i(SplashActivity.TAG, "nentro usecase")
        val quote: QuotesModel? = firestoreRepository.getRandomQuote()
        Log.i(SplashActivity.TAG, "nota en usecase $quote")
        if (quote != null) {
            return quote
        }
        else return null
    }

}