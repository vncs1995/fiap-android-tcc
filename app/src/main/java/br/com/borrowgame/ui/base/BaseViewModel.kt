package br.com.borrowgame.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.usecases.app.GetMinAppVersionUseCase
import kotlinx.coroutines.launch

class BaseViewModel(
    private val getMinAppVersionUseCase: GetMinAppVersionUseCase
) : ViewModel() {
    var minVersionAppState = MutableLiveData<RequestState<Int>>()
    fun getMinVersion() {
        viewModelScope.launch {
            minVersionAppState.value =
                getMinAppVersionUseCase.getMinVersionApp()
        }
    }
}