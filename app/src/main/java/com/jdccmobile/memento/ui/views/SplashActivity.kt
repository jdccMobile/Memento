package com.jdccmobile.memento.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jdccmobile.memento.R
import com.jdccmobile.memento.databinding.ActivityQuoteBinding
import com.jdccmobile.memento.databinding.ActivitySplashBinding
import com.jdccmobile.memento.ui.viewModels.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        const val TAG = "jose d"
        const val STOICISM_COLL = "CitasEstoicas"
    }

    private val viewModel by viewModels<QuotesViewModel>()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        screenSplash.setKeepOnScreenCondition { true }

        checkLastDay()

    }

    // Check the last day that the app has been initialized
    private fun checkLastDay() {
        val currentDay = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("D")).toInt()
        val lastDay = viewModel.getLastDay()

        if (lastDay != null) {
            if (currentDay > lastDay) {
                viewModel.saveLastDay(currentDay)
                getNewQuote()
            } else {
                val intent = Intent(this, QuotesActivity::class.java)
                intent.putExtra("quote", viewModel.getLastQuote())  // todo cambiar autor por quotesmodel
                intent.putExtra("author", viewModel.getLastAuthor())
                startActivity(intent)
                finish()
            }
        } else {
            viewModel.saveLastDay(currentDay)
            getNewQuote()
        }
    }

    private fun getNewQuote() {
        viewModel.getQuoteFirestore()
        viewModel.quotesModel.observe(this) { quotesModel ->
            if (quotesModel != null) {
                viewModel.saveLastQuote(quotesModel.quote)
                viewModel.saveLastAuthor(quotesModel.author)
                val intent = Intent(this, QuotesActivity::class.java)
                intent.putExtra("quote", quotesModel.quote)
                intent.putExtra("author", quotesModel.author)
                startActivity(intent)
                finish()
            }
        }
    }

}