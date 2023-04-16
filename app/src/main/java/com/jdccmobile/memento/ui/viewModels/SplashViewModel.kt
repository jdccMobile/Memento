package com.jdccmobile.memento.ui.viewModels

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.domain.firestore.GetRandomQuoteUseCase
import com.jdccmobile.memento.domain.preferences.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import android.util.Log
import com.jdccmobile.memento.ui.views.SplashActivity


const val QUOTE = "QUOTE"
const val AUTHOR = "AUTHOR"
const val LAST_DAY = "LAST_DAY"
const val LAST_QUOTE_FAV = "LAST_QUOTE_FAV"

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val getLastQuoteUseCase: GetLastQuoteUseCase,
    private val saveLastQuoteUseCase: SaveLastQuoteUseCase,
    private val getLastAuthorUseCase: GetLastAuthorUseCase,
    private val saveLastAuthorUseCase: SaveLastAuthorUseCase,
    private val saveLastDayUseCase: SaveLastDayUseCase,
    private val getLastDayUseCase: GetLastDayUseCase,
    private val saveFavCurrentQuoteUC: SaveFavCurrentQuoteUC
) : ViewModel() {

    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val quotesModel = MutableLiveData<QuotesModel>()

    fun onCreateView() {
        val currentDay = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("D")).toInt()
        val lastDay = getLastDay()

        timerWaitConexion()

        if (lastDay != null) {
            if (currentDay > lastDay) {
                saveLastDay(currentDay)
                resetFavIcon()
                getQuoteFirestore()

            } else {
                val quote = QuotesModel(getLastQuote()!!, getLastAuthor()!!)
                quotesModel.postValue(quote)
            }
        } else {
            saveLastDay(currentDay)
            resetFavIcon()
            getQuoteFirestore()

        }
    }


    private fun timerWaitConexion() {
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val quote = QuotesModel("Sin conexión a internet", "Aristoteles")
                quotesModel.postValue(quote)
            }
        }
        timer.start()
    }


    private fun getQuoteFirestore() {
        viewModelScope.launch {
            val quote: QuotesModel? = getRandomQuoteUseCase()
            if (quote != null) {
                saveLastQuote(quote.quote)
                if (quote.quote == "Sin conexión a internet") saveLastDay(0) // para que si volvemos a entrar genere una nueva cita
                saveLastAuthor(quote.author)
                quotesModel.postValue(quote)
            }
        }
    }


    private fun resetFavIcon() {
        viewModelScope.launch {
            saveFavCurrentQuoteUC(false)
        }
    }


    private fun saveLastDay(lastDay: Int) {
        viewModelScope.launch {
            saveLastDayUseCase(lastDay)
        }
    }


    private fun getLastDay(): Int? = runBlocking { getLastDayUseCase() }


    private fun saveLastQuote(quote: String) {
        viewModelScope.launch {
            saveLastQuoteUseCase(quote)
        }
    }


    private fun getLastQuote(): String? = runBlocking { getLastQuoteUseCase() }


    private fun saveLastAuthor(author: String) {
        viewModelScope.launch {
            saveLastAuthorUseCase(author)
        }
    }


    private fun getLastAuthor(): String? = runBlocking { getLastAuthorUseCase() }
}