package com.jdccmobile.memento.domain.preferences

import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.views.SplashActivity.Companion.AUTHOR
import javax.inject.Inject


class GetLastAuthorUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke() : String? = dataStoreRepository.getString(AUTHOR).orEmpty()
}