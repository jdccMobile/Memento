package com.jdccmobile.memento.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jdccmobile.memento.databinding.ActivitySplashBinding
import com.jdccmobile.memento.ui.viewModels.QuotesViewModel
import com.jdccmobile.memento.ui.viewModels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        const val TAG = "josed"
        const val QUOTE = "QUOTE"
        const val QUOTE_INTENT = "quote"
        const val AUTHOR = "AUTHOR"
        const val AUTHOR_INTENT = "author"
        const val LAST_DAY = "LAST_DAY"
        const val LAST_QUOTE_FAV = "LAST_QUOTE_FAV"
        const val NOTIFICATION_ENABLED = "NOTIFICATION_ENABLED"
    }

    private val viewModel by viewModels<SplashViewModel>()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        screenSplash.setKeepOnScreenCondition { true }

        viewModel.onCreateView()
        navigateToQuotes()

    }

    private fun navigateToQuotes() {
        viewModel.quotesModel.observe(this){ quotesModel ->
            if(quotesModel != null){
                val intent = Intent(this, QuotesActivity::class.java)
                intent.putExtra(QUOTE_INTENT, quotesModel.quote)
                intent.putExtra(AUTHOR_INTENT, quotesModel.author)
                startActivity(intent)
                finish()
            }
        }
    }

}