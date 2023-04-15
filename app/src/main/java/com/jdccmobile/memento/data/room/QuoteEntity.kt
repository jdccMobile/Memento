package com.jdccmobile.memento.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdccmobile.memento.data.model.QuotesModel

@Entity(tableName = "fav_quotes")
data class QuoteEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") val id: Int = 0, // para que no haya dos citas con el mismo id
    @ColumnInfo(name="quote") val quote: String, // column info lo que guarda en la base de datos
    @ColumnInfo(name="author") val author: String
)

fun QuotesModel.toRoom() = QuoteEntity(quote = quote, author = author)