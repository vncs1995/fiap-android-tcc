package br.com.borrowgame.domain.usecases.app

import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.repository.AppRepository

class GetMinAppVersionUseCase(
    private val appRespository: AppRepository
) {
    suspend fun getMinVersionApp(): RequestState<Int> =
        appRespository.getMinVersionApp()
}