package com.jdccmobile.memento.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {

    @Query("SELECT * FROM fav_quotes") // seleciona todo de esa tabla
    suspend fun getAllFavQuotes(): List<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavQuote(quote: QuoteEntity)

    @Query("DELETE FROM fav_quotes")
    suspend fun delAllFavQuotes()

    @Query("DELETE FROM fav_quotes WHERE quote = :quote")
    suspend fun delFavQuote(quote : String)
}