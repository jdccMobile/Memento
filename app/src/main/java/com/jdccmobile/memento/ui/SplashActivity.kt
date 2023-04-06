package com.jdccmobile.memento.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jdccmobile.memento.R
import com.jdccmobile.memento.ui.quotes.QuotesActivity
import com.jdccmobile.memento.ui.quotes.QuotesViewModel

class SplashActivity : AppCompatActivity() {

    // Todo cambiar imagen de splash screen
    private val viewModel by viewModels<QuotesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        screenSplash.setKeepOnScreenCondition{ true }

        // Wait until the data is loaded
        viewModel.getQuoteFirestore()
        viewModel.quotesModel.observe(this) { quote ->
            if (quote != null) {
                val intent = Intent(this, QuotesActivity::class.java)
                intent.putExtra("quote", quote.quote)
                intent.putExtra("author", quote.author)
                startActivity(intent)
                finish()
            }
        }


    }
}