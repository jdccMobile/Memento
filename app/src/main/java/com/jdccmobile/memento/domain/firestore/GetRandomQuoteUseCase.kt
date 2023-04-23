package com.jdccmobile.memento.domain.firestore

import android.util.Log
import com.jdccmobile.memento.data.firebase.FirestoreRepository
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.ui.views.SplashActivity
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) {

    suspend operator fun invoke():QuotesModel? {
        Log.w(SplashActivity.TAG, "firestore use case")
        val quote: QuotesModel? = firestoreRepository.getRandomQuote()
        if (quote != null) {
            return quote
        }
        else return null
    }

}