package com.jdccmobile.memento.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QuoteEntity::class], version = 1)
abstract class FavQuoteDatabase: RoomDatabase() {

    abstract fun getQuoteDao():QuoteDao
}