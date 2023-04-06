package com.jdccmobile.memento.data.local

import com.jdccmobile.memento.data.QuotesModel

class QuotesProvider {
    companion object{
        fun randomQuote(): QuotesModel {
            var pos = (quotes.indices).random()
            return quotes[pos]
        }

        private val quotes = listOf<QuotesModel>(
            QuotesModel("Sé libre", "Yo"),
            QuotesModel("Come todo lo que puedas", "Michu"),
            QuotesModel("Pelotina", "Nala")
        )
    }
}