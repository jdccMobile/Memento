package com.jdccmobile.memento.ui.views

import com.google.android.gms.ads.AdRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdccmobile.memento.databinding.ActivityFavoritesBinding
import com.jdccmobile.memento.ui.adapters.FavQuoteAdapter
import com.jdccmobile.memento.ui.viewModels.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesActivity @Inject constructor() : AppCompatActivity() {
    private val viewModel by viewModels<FavoritesViewModel>()
    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        viewModel.onCreateView()
        initRecyclerView()
        loadAds()
    }

    private fun initRecyclerView() {
                viewModel.favoritesModel.observe(this){ quotesModel ->
            if(quotesModel != null){
                binding.rvFavQuotes.layoutManager = LinearLayoutManager(this)
                binding.rvFavQuotes.adapter = FavQuoteAdapter(quotesModel)
            }else{
                //todo poner progress bar
            }
        }
    }


    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adFavorites.loadAd(adRequest)
    }


}