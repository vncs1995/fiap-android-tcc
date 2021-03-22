package br.com.borrowgame.data.repository

import br.com.borrowgame.data.remote.datasource.AppRemoteDataSource
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.domain.repository.AppRepository

class AppRepositoryImpl(
    private val appRemoteDataSource: AppRemoteDataSource
) : AppRepository {
    override suspend fun getMinVersionApp(): RequestState<Int>
    {
        return appRemoteDataSource.getMinVersionApp()
    }
}