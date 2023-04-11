package com.jdccmobile.memento.domain.preferences

import android.util.Log
import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.viewModels.LAST_DAY
import com.jdccmobile.memento.ui.viewModels.QUOTE
import com.jdccmobile.memento.ui.views.SplashActivity
import javax.inject.Inject


class SaveLastDayUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(lastDay: Int) {
        Log.i(SplashActivity.TAG, "ultimo dia $lastDay")
        dataStoreRepository.putInt(LAST_DAY, lastDay)
    }

}