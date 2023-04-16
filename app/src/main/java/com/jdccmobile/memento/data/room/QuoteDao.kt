package com.jdccmobile.memento.data.room

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdccmobile.memento.ui.views.SplashActivity

@Dao
interface QuoteDao {

    @Query("SELECT * FROM fav_quotes") // seleciona todo de esa tabla
    suspend fun getAllFavQuotes(): List<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavQuote(quote: QuoteEntity)

    @Query("DELETE FROM fav_quotes")
    suspend fun delAllFavQuotes()
}