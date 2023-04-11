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

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val getLastQuoteUseCase: GetLastQuoteUseCase,
    private val saveLastQuoteUseCase: SaveLastQuoteUseCase,
    private val getLastAuthorUseCase: GetLastAuthorUseCase,
    private val saveLastAuthorUseCase: SaveLastAuthorUseCase,
    private val saveLastDayUseCase: SaveLastDayUseCase,
    private val getLastDayUseCase: GetLastDayUseCase
) : ViewModel() {

    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val quotesModel = MutableLiveData<QuotesModel>()

    fun onCreateView(){
        Log.w(SplashActivity.TAG, "Creo la vista")
        val currentDay = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("D")).toInt()
        val lastDay = getLastDay()

        timerWaitConexion()

        Log.i(SplashActivity.TAG, "ultimo dia $lastDay")
        if (lastDay != null) {
            if (currentDay > lastDay) {
                Log.w(SplashActivity.TAG, "llamo firestore1")
                saveLastDay(currentDay)
                getQuoteFirestore()

            } else {
                val quote = QuotesModel(getLastQuote()!!, getLastAuthor()!!)
                quotesModel.postValue(quote)
            }
        } else {
            Log.w(SplashActivity.TAG, "llamo firestore 2")
            saveLastDay(currentDay)
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

    fun getQuoteFirestore(){
        viewModelScope.launch {
            val quote: QuotesModel? = getRandomQuoteUseCase()
            if(quote != null){
                saveLastQuote(quote.quote)
                if(quote.quote == "Sin conexión a internet") saveLastDay(0) // para que si volvemos a entrar genere una nueva cita
                saveLastAuthor(quote.author)
                quotesModel.postValue(quote)
            }
        }
    }

    fun saveLastDay(lastDay: Int) {
        viewModelScope.launch {
            saveLastDayUseCase(lastDay)
        }
    }

    fun getLastDay(): Int? = runBlocking { getLastDayUseCase() }

    fun saveLastQuote(quote: String) {
        viewModelScope.launch {
            saveLastQuoteUseCase(quote)
        }
    }

    fun getLastQuote(): String? = runBlocking { getLastQuoteUseCase() }

    fun saveLastAuthor(author: String) {
        viewModelScope.launch {
            saveLastAuthorUseCase(author)
        }
    }

    fun getLastAuthor(): String? = runBlocking {  getLastAuthorUseCase() }
}