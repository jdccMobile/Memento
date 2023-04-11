package com.jdccmobile.memento.ui.viewModels

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.domain.GetRandomQuoteUseCase
import com.jdccmobile.memento.ui.views.QuotesActivity
import com.jdccmobile.memento.ui.views.SplashActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

const val QUOTE = "QUOTE"
const val AUTHOR = "AUTHOR"
const val LAST_DAY = "LAST_DAY"

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
) : ViewModel() {

    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val quotesModel = MutableLiveData<QuotesModel>()

    fun onCreateView(){
        val currentDay = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("D")).toInt()
        val lastDay = getLastDay()

        if (lastDay != null) {
            if (currentDay > lastDay) {
                saveLastDay(currentDay)
                getQuoteFirestore()
            } else {
                val quote = QuotesModel(getLastQuote()!!, getLastAuthor()!!) // todo cambiar como recuperar los datos
                quotesModel.postValue(quote)
            }
        } else {
            saveLastDay(currentDay)
            getQuoteFirestore()
        }
    }

    // todo pasar a use case
    fun getQuoteFirestore(){
        viewModelScope.launch {
            val quote: QuotesModel? = getRandomQuoteUseCase()
            if(quote != null){
                saveLastQuote(quote.quote)
                saveLastAuthor(quote.author)
                quotesModel.postValue(quote)
            }
        }
    }

    fun saveLastDay(lastDay: Int) {
        viewModelScope.launch {
            dataStoreRepository.putInt(LAST_DAY, lastDay)
        }
    }

    fun getLastDay(): Int? = runBlocking { dataStoreRepository.getInt(LAST_DAY) }

    fun saveLastQuote(quote: String) {
        viewModelScope.launch {
            dataStoreRepository.putString(QUOTE, quote)
        }
    }

    fun getLastQuote(): String? = runBlocking { dataStoreRepository.getString(QUOTE).orEmpty() }

    fun saveLastAuthor(author: String) {
        viewModelScope.launch {
            dataStoreRepository.putString(AUTHOR, author)
        }
    }

    fun getLastAuthor(): String? = runBlocking { dataStoreRepository.getString(AUTHOR).orEmpty() }
}