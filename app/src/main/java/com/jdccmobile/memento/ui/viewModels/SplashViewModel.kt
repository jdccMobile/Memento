package com.jdccmobile.memento.ui.viewModels

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdccmobile.memento.R
import com.jdccmobile.memento.core.AlarmNotification
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.domain.firestore.GetRandomQuoteUseCase
import com.jdccmobile.memento.domain.preferences.*
import com.jdccmobile.memento.ui.views.SplashActivity
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.MY_CHANNEL_ID
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.NOTIFICATION_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val getLastQuoteUseCase: GetLastQuoteUseCase,
    private val saveLastQuoteUseCase: SaveLastQuoteUseCase,
    private val getLastAuthorUseCase: GetLastAuthorUseCase,
    private val saveLastAuthorUseCase: SaveLastAuthorUseCase,
    private val saveLastDayUseCase: SaveLastDayUseCase,
    private val getLastDayUseCase: GetLastDayUseCase,
    private val saveNotiConfUseCase: SaveNotiConfUseCase,
    private val getNotiConfUseCase: GetNotiConfUseCase,
    private val application: Application
) : ViewModel() {

    // Create MutableLiveData which MainFragment can subscribe to
    // When this data changes, it triggers the UI to do an update
    val quotesModel = MutableLiveData<QuotesModel>()

    fun onCreateView() {
        createChannel()
        val currentDay = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("D")).toInt()
        val lastDay = getLastDay()

        timerWaitConexion()

        // One quote per day
        if (lastDay != null) {
            if (currentDay > lastDay) {
                Log.i(SplashActivity.TAG, "Nueva cita")
                saveLastDay(currentDay)
                getQuoteFirestore()

            } else {
                Log.i(SplashActivity.TAG, "Vieja cita")
                val quote = QuotesModel(getLastQuote(), getLastAuthor())
                quotesModel.postValue(quote)
            }
        } else {
            // First time open the app
            Log.i(SplashActivity.TAG, "Nueva cita 2")
            saveLastDay(currentDay)
            setValueNotiConf()
            getQuoteFirestore()

        }
    }

    // Max time duratio of the splash screen
    private fun timerWaitConexion() {
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val quote = QuotesModel(
                    application.applicationContext.getString(R.string.no_internet_connection),
                    "Aristoteles"
                )
                quotesModel.postValue(quote)
            }
        }
        timer.start()
    }


    private fun getQuoteFirestore() {
        viewModelScope.launch {
            Log.i(SplashActivity.TAG, "Pillar cita firebase")
            val quote: QuotesModel? = getRandomQuoteUseCase()
            if (quote != null) {
                saveLastQuote(quote.quote)
                if (quote.quote == "Sin conexión a internet") saveLastDay(0) // para que si volvemos a entrar genere una nueva cita
                saveLastAuthor(quote.author)
                quotesModel.postValue(quote)
            }
        }
    }

    // For versions > Android 8
    private fun createChannel() {

        Log.i(SplashActivity.TAG, "Crear canal notifiaciones")
        val channel = NotificationChannel(
            MY_CHANNEL_ID,
            "MyChannel26",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel for APIs > 26"
        }
        val notificationManager: NotificationManager =
            application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }


    private fun setValueNotiConf() {
        viewModelScope.launch {
            saveNotiConfUseCase(true)
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


    private fun getLastQuote(): String = runBlocking { getLastQuoteUseCase() }


    private fun saveLastAuthor(author: String) {
        viewModelScope.launch {
            saveLastAuthorUseCase(author)
        }
    }


    private fun getLastAuthor(): String = runBlocking { getLastAuthorUseCase() }


    fun createDailyNotification() {
        Log.i(SplashActivity.TAG, "crear notificacion")
        val notificationConf = getNotiConf()
        Log.i(SplashActivity.TAG, "valor notifiacion $notificationConf")
        if (notificationConf != null && notificationConf == true) {
            val intent = Intent(application.applicationContext, AlarmNotification::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                application.applicationContext,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 12)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }


    private fun getNotiConf(): Boolean? = runBlocking { getNotiConfUseCase() }


}
