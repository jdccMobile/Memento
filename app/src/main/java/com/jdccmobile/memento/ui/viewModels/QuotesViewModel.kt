package com.jdccmobile.memento.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.STOICISM_COLL
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

const val QUOTE = "QUOTE"
const val AUTHOR = "AUTHOR"
const val LAST_DAY = "LAST_DAY"

@HiltViewModel // Para inyectar
class QuotesViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() { // Para ser inyectada

    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val quotesModel = MutableLiveData<QuotesModel>()
    private val db = Firebase.firestore

    fun getQuoteFirestore() {
        db.collection(STOICISM_COLL).get()
            .addOnSuccessListener { collection ->
                val randomPos = (0 until collection.size()).random()
                val randomDoc = collection.documents[randomPos]
                if (randomDoc != null) {
                    val quote = randomDoc.toObject(QuotesModel::class.java)
                    quotesModel.postValue(quote)
                } else {
                    Log.w(TAG, "Documento vacio.")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun saveLastDay(lastDay: Int) {
        viewModelScope.launch {
            repository.putInt(LAST_DAY, lastDay)
        }
    }

    fun getLastDay(): Int? = runBlocking { repository.getInt(LAST_DAY) }

    fun saveLastQuote(quote: String) {
        viewModelScope.launch {
            repository.putString(QUOTE, quote)
        }
    }

    fun getLastQuote(): String? = runBlocking { repository.getString(QUOTE).orEmpty() }

    fun saveLastAuthor(author: String) {
        viewModelScope.launch {
            repository.putString(AUTHOR, author)
        }
    }

    fun getLastAuthor(): String? = runBlocking { repository.getString(AUTHOR).orEmpty() }

}


// Get the updated text from our model and post the value to MainFragment
//    fun getQuote() {
//        val quote = QuotesProvider.randomQuote()
//        quotesModel.postValue(quote)
//    }