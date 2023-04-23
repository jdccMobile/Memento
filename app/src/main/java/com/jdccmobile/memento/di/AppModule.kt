package com.jdccmobile.memento.di

import android.content.Context
import androidx.room.Room
import com.jdccmobile.memento.data.firebase.FirestoreRepository
import com.jdccmobile.memento.data.firebase.FirestoreRepositoryImp
import com.jdccmobile.memento.data.preferences.DataStoreRepositoryImp
import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.data.room.FavQuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app: Context
    ): DataStoreRepository = DataStoreRepositoryImp(app)

    @Singleton
    @Provides
    fun provideFirestoreRepository(): FirestoreRepository = FirestoreRepositoryImp()

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, FavQuoteDatabase::class.java, "fav_quotes_database").build()

    @Singleton
    @Provides
    fun providesQuoteDao(db: FavQuoteDatabase) = db.getQuoteDao()
}