package com.jdccmobile.memento.ui.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jdccmobile.memento.R
import com.jdccmobile.memento.data.model.QuotesModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FavQuoteAdapter @Inject constructor(
    private val favQuotesList: List<QuotesModel>
) : RecyclerView.Adapter<FavQuoteAdapter.FavQuoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavQuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_quote_item, parent, false)
        return FavQuoteViewHolder(view)
    }

    override fun getItemCount(): Int = favQuotesList.size

    override fun onBindViewHolder(holder: FavQuoteViewHolder, position: Int) {
        val item = favQuotesList[position]
        holder.render(item)
    }

    inner class FavQuoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val favQuote = itemView.findViewById<TextView>(R.id.tvFavQuote)
        val favAuthor = itemView.findViewById<TextView>(R.id.tvFavAuthor)

        fun render(quote: QuotesModel){
            favQuote.text = quote.quote
            favAuthor.text = quote.author

            // Agrega la animación personalizada aquí
            val anim = ObjectAnimator.ofFloat(itemView, View.ALPHA, 0f, 1f)
            anim.duration = 500
            anim.start()
        }
    }
}

