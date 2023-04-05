package com.jdccmobile.memento.data.local

class QuotesProvider {
    companion object{
        fun randomQuote(): QuotesModel{
            var pos = (quotes.indices).random()
            return quotes[pos]
        }

        private val quotes = listOf<QuotesModel>(
            QuotesModel(1, "Sé libre", "Yo"),
            QuotesModel(2, "Come todo lo que puedas", "Michu"),
            QuotesModel(3, "Pelotina", "Nala")
        )
    }
}