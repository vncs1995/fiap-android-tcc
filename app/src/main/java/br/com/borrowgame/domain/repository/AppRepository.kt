package br.com.borrowgame.domain.repository

import br.com.borrowgame.domain.entity.RequestState

interface AppRepository {
    suspend fun getMinVersionApp(): RequestState<Int>
}