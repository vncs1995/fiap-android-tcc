package br.com.borrowgame.data.remote.datasource

import br.com.borrowgame.domain.entity.RequestState

interface AppRemoteDataSource {
    suspend fun getMinVersionApp(): RequestState<Int>
}