package com.jdccmobile.memento.ui.quotes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.ui.quotes.QuotesActivity.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel // Para inyectar
class QuotesViewModel @Inject constructor() : ViewModel() { // Para ser inyectada

    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val quotesModel = MutableLiveData<QuotesModel>()
    private val db = Firebase.firestore

    fun getQuoteFirestore(){
        db.collection("CitasEstoicas").document("1").get()
            .addOnSuccessListener { doc ->
                if(doc != null){
                    val quote = doc.toObject(QuotesModel::class.java)
                    quotesModel.postValue(quote)
                } else{
                    Log.w(TAG, "Documento vacio.")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}












// Get the updated text from our model and post the value to MainFragment
//    fun getQuote() {
//        val quote = QuotesProvider.randomQuote()
//        quotesModel.postValue(quote)
//    }