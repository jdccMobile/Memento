package com.jdccmobile.memento.ui.views

import com.google.android.gms.ads.AdRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdccmobile.memento.R
import com.jdccmobile.memento.databinding.ActivityFavoritesBinding
import com.jdccmobile.memento.ui.adapters.FavQuoteAdapter
import com.jdccmobile.memento.ui.viewModels.FavoritesViewModel
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesActivity @Inject constructor() : AppCompatActivity() {
    private val viewModel by viewModels<FavoritesViewModel>()
    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: FavQuoteAdapter

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
        viewModel.favoritesModel.observe(this) { quotesModel ->
            Log.i(TAG, "view $quotesModel")
            if (quotesModel != null) {
                adapter = FavQuoteAdapter(
                    favQuotesList = quotesModel,
                    onClickListener = { quote, pos -> onDeletedItem(quote, pos) })
                binding.rvFavQuotes.layoutManager = LinearLayoutManager(this)
                binding.rvFavQuotes.adapter = adapter
            }
        }
    }

    private fun onDeletedItem(quote: String, pos: Int) {
        Log.i(TAG, "quote $quote")
        createDeleteDialog(quote, pos)

    }

    private fun createDeleteDialog(quote: String, pos: Int) {
        val builder = AlertDialog.Builder(this)
        val customDialog = LayoutInflater.from(this).inflate(R.layout.del_fav_quotes_dialog, null)
        builder.setView(customDialog)
        val dialog = builder.create()

        val tvInfoDelete = customDialog.findViewById<TextView>(R.id.tvInfoDelete)
        val btDelete = customDialog.findViewById<Button>(R.id.btDelete)
        val btCancelDelete = customDialog.findViewById<Button>(R.id.btCancelDelete)

        tvInfoDelete.text = getString(R.string.delete_advise)
        btDelete.setOnClickListener {

            viewModel.deleteFavQuote(quote)
            adapter.notifyItemRemoved(pos)
            Toast.makeText(this, getString(R.string.delete_quote_realized), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        btCancelDelete.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }


    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adFavorites.loadAd(adRequest)
    }


}