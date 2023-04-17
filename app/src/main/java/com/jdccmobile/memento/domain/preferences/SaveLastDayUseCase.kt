package com.jdccmobile.memento.domain.preferences

import android.util.Log
import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.views.SplashActivity
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.LAST_DAY
import javax.inject.Inject


class SaveLastDayUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(lastDay: Int) {
        Log.i(SplashActivity.TAG, "ultimo dia $lastDay")
        dataStoreRepository.putInt(LAST_DAY, lastDay)
    }

}