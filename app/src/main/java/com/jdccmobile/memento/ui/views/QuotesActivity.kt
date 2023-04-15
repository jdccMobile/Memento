package com.jdccmobile.memento.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.jdccmobile.memento.R
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.databinding.ActivityQuoteBinding
import com.jdccmobile.memento.ui.viewModels.QuotesViewModel
import com.jdccmobile.memento.ui.viewModels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuotesActivity : AppCompatActivity() {

    private var isFavourite = false // todo quitar y guardar en data store

    private val viewModel by viewModels<QuotesViewModel>()

    private lateinit var binding: ActivityQuoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initQuote()
        initListener()
    }


    private fun initQuote() {
        val quote = intent.getStringExtra("quote")
        val author = intent.getStringExtra("author")
        binding.tvQuote.text = quote
        binding.tvAuthor.text = author
    }


    private fun initListener() {
        binding.ivShare.setOnClickListener { } // Todo añadir compartir
        binding.ivHome.setOnClickListener { navigateToMenu() }
        binding.ivLike.setOnClickListener {
            viewModel.saveFavQuote(QuotesModel(binding.tvQuote.text.toString(), binding.tvAuthor.text.toString()))
            changeHeartColor()
        }
    }


    private fun navigateToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }


    private fun changeHeartColor() {
        // todo pasar corazon a datastore cuando haga los ajustes
        if(isFavourite){
            isFavourite = !isFavourite
            binding.ivLike.setImageResource(R.drawable.ic_heart)
        }
        else{
            isFavourite = !isFavourite
            binding.ivLike.setImageResource(R.drawable.ic_red_heart)
        }
    }

}



