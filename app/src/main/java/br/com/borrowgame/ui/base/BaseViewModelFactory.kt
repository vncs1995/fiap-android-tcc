package br.com.borrowgame.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.borrowgame.domain.usecases.app.GetMinAppVersionUseCase

class BaseViewModelFactory(
    private val getMinAppVersionUseCase: GetMinAppVersionUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetMinAppVersionUseCase::class.java)
            .newInstance(getMinAppVersionUseCase)
    }
}

