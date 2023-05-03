package com.jdccmobile.memento.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.ads.AdRequest
import com.jdccmobile.memento.R
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.databinding.ActivityQuoteBinding
import com.jdccmobile.memento.ui.viewModels.QuotesViewModel
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.AUTHOR_INTENT
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.QUOTE_INTENT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuotesActivity : AppCompatActivity() {

    private var quote = ""
    private var author = ""
    private val viewModel by viewModels<QuotesViewModel>()

    private lateinit var binding: ActivityQuoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initListener()
    }

    private fun initUI() {
        initQuote()
        loadAds()

    }


    private fun initQuote() {
        quote = intent.getStringExtra(QUOTE_INTENT)!!
        author = intent.getStringExtra(AUTHOR_INTENT)!!
        binding.tvQuote.text = quote
        binding.tvAuthor.text = author
    }


    private fun initListener() {
        binding.ivShare.setOnClickListener { shareQuote() }
        binding.ivHome.setOnClickListener { navigateToMenu() }
        binding.isFav.setOnClickListener {
            saveQuoteInFav()
        }
    }

    private fun shareQuote() {
        val textToShare =
            getString(R.string.as_he_said) + " $author: '$quote'. " + getString(R.string.discover_new_cotes)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToShare)
            type = "text/plain"
        }

        val finalShareIntent = Intent.createChooser(shareIntent, null)
        this.startActivity(finalShareIntent)
    }


    private fun navigateToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }


    private fun saveQuoteInFav() {
            viewModel.saveFavQuote(QuotesModel(binding.tvQuote.text.toString(), binding.tvAuthor.text.toString()))
            Toast.makeText(this, getString(R.string.save_quote_realized), Toast.LENGTH_SHORT).show()
    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adQuote.loadAd(adRequest)
    }

}



