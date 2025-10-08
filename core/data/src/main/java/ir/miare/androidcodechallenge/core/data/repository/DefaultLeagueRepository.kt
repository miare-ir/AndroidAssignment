package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.data.model.asFakeData
import ir.miare.androidcodechallenge.core.domain.LeagueRepository
import ir.miare.androidcodechallenge.core.model.FakeData
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
    override fun getHome(): Flow<ApiResult<List<FakeData>>> = flow{
        when (val result = networkDataSource.getHome()) {
            is ApiResult.Success -> {
                val data = result.data.map(NetworkFakeData::asFakeData)
                emit(ApiResult.Success(data))
            }

            is ApiResult.Error -> {
                emit(ApiResult.Error(result.throwable))
            }
        }
    }
}