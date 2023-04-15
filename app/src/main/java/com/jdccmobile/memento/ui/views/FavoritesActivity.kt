package com.jdccmobile.memento.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.jdccmobile.memento.R
import com.jdccmobile.memento.databinding.ActivityFavoritesBinding
import com.jdccmobile.memento.databinding.ActivitySplashBinding
import com.jdccmobile.memento.ui.viewModels.FavoritesViewModel
import com.jdccmobile.memento.ui.viewModels.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesActivity @Inject constructor() : AppCompatActivity() {
    private val viewModel by viewModels<FavoritesViewModel>()
    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.onCreateView()
        printQuote()
    }

    private fun printQuote() {
        viewModel.favoritesModel.observe(this){ quotesModel ->
            if(quotesModel != null){
                binding.tvCita.text = quotesModel.quote
                binding.tvAutor.text = quotesModel.author
            }
        }
    }


}