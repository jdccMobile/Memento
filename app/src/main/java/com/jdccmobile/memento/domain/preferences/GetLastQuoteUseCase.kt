package com.jdccmobile.memento.domain.preferences

import com.jdccmobile.memento.data.preferences.DataStoreRepository
import com.jdccmobile.memento.ui.viewModels.QUOTE
import javax.inject.Inject


class GetLastQuoteUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke() : String? = dataStoreRepository.getString(QUOTE).orEmpty()
}