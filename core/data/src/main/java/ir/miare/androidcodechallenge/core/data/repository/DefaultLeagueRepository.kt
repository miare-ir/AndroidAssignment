package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.domain.LeagueRepository
import ir.miare.androidcodechallenge.core.network.NetworkDataSource
import ir.miare.androidcodechallenge.core.network.model.NetworkFakeData
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultLeagueRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : LeagueRepository {
    override fun getHome(): Flow<ApiResult<NetworkFakeData>> = flow {
        emit(networkDataSource.getHome())
    }
}