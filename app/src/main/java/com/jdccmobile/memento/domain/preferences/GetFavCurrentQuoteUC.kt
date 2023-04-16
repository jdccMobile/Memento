package com.jdccmobile.memento.domain.preferences

import android.util.Log
import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.viewModels.AUTHOR
import com.jdccmobile.memento.ui.viewModels.LAST_QUOTE_FAV
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.TAG
import javax.inject.Inject

class GetFavCurrentQuoteUC @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke() : Boolean? = dataStoreRepository.getBool(LAST_QUOTE_FAV)
}