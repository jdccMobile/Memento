package com.jdccmobile.memento.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jdccmobile.memento.R
import com.jdccmobile.memento.data.model.QuotesModel
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.TAG
import javax.inject.Inject

class FavQuoteAdapter @Inject constructor(
    private val favQuotesList: List<QuotesModel>,
    private val onClickListener: (String, Int) -> Unit
) : RecyclerView.Adapter<FavQuoteAdapter.FavQuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavQuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_quote_item, parent, false)
        return FavQuoteViewHolder(view)
    }

    override fun getItemCount(): Int = favQuotesList.size

    override fun onBindViewHolder(holder: FavQuoteViewHolder, position: Int) {
        val item = favQuotesList[position]
        holder.render(item, onClickListener)
    }


    // ViewHolder
    inner class FavQuoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val favQuote = itemView.findViewById<TextView>(R.id.tvFavQuote)
        private val favAuthor = itemView.findViewById<TextView>(R.id.tvFavAuthor)

        fun render(quote: QuotesModel, onClickListener: (String, Int) -> Unit) {
            Log.i(TAG, "RENDER")
            favQuote.text = quote.quote
            favAuthor.text = quote.author
            itemView.setOnClickListener {
                Log.i(TAG, adapterPosition.toString())
                onClickListener(quote.quote, adapterPosition)
            }

        }
    }
}

