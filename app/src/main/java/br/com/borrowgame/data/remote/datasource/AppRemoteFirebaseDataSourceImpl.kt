package br.com.borrowgame.data.remote.datasource

import android.util.Log
import br.com.borrowgame.domain.entity.RequestState
import br.com.borrowgame.extensions.fromRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AppRemoteFirebaseDataSourceImpl: AppRemoteDataSource {
    override suspend fun getMinVersionApp(): RequestState<Int> {
        return try {
            val minVersion = Gson().fromRemoteConfig("", Int::class.java) ?: 0
            Log.d("REMOTECONFIG", "MIN VERSION: "+ minVersion)
            RequestState.Success(minVersion)
        } catch(e: java.lang.Exception){
            RequestState.Error(e)
        }

    }
}