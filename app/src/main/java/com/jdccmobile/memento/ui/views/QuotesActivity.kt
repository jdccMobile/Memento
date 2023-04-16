package com.jdccmobile.memento.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.jdccmobile.memento.R
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.databinding.ActivityQuoteBinding
import com.jdccmobile.memento.ui.viewModels.QuotesViewModel
import com.jdccmobile.memento.ui.viewModels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.checkerframework.common.returnsreceiver.qual.This

@AndroidEntryPoint
class QuotesActivity : AppCompatActivity() {

    private var isFavorite = false // todo quitar y guardar en data store

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
        viewModel.onCreateView()
        initQuote()
        initFav()
    }


    private fun initQuote() {
        val quote = intent.getStringExtra("quote")
        val author = intent.getStringExtra("author")
        binding.tvQuote.text = quote
        binding.tvAuthor.text = author
    }


    private fun initFav() {
        viewModel.isFavorite.observe(this){ isFav ->
            isFavorite = isFav
            if(isFav) binding.isFav.setImageResource(R.drawable.ic_red_heart)
            else binding.isFav.setImageResource(R.drawable.ic_heart)
        }
    }


    private fun initListener() {
        binding.ivShare.setOnClickListener { } // Todo añadir compartir
        binding.ivHome.setOnClickListener { navigateToMenu() }
        binding.isFav.setOnClickListener { changeHeartColor() }
    }


    private fun navigateToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }


    private fun changeHeartColor() {
        if(!isFavorite){
            viewModel.saveIsCurrentFav()
            viewModel.saveFavQuote(QuotesModel(binding.tvQuote.text.toString(), binding.tvAuthor.text.toString()))
            binding.isFav.setImageResource(R.drawable.ic_red_heart)
            Toast.makeText(this, "Cita grabada en favoritos", Toast.LENGTH_SHORT).show()
        }
    }

}



