package com.jdccmobile.memento.data.firebase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.ui.views.SplashActivity
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

const val STOICISM_COLL = "CitasEstoicas"

class FirestoreRepositoryImp @Inject constructor(): FirestoreRepository {

    override suspend fun getRandomQuote(): QuotesModel? {

        val db = Firebase.firestore

        return suspendCoroutine { continuation ->
            db.collection(STOICISM_COLL).get()
                .addOnSuccessListener { collection ->
                    val randomPos = (0 until collection.size()).random()
                    val randomDoc = collection.documents[randomPos]
                    if (randomDoc != null) {
                        val quote = randomDoc.toObject(QuotesModel::class.java)
                        continuation.resume(quote)
                    } else {
                        Log.w(SplashActivity.TAG, "Documento vacio.")
                        continuation.resume(null)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(SplashActivity.TAG, "Error getting documents.", exception)
                    continuation.resume(null)
                }
        }
    }
}