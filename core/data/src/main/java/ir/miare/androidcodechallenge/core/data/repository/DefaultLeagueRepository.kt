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
    override suspend fun getHome(): ApiResult<Flow<List<FakeData>>> {
        return when (val result = networkDataSource.getHome()) {
            is ApiResult.Success -> {
                ApiResult.Success(
                    flow {
                        emit(
                            result.data.map(NetworkFakeData::asFakeData)
                        )
                    }
                )
            }

            is ApiResult.Error -> {
                ApiResult.Error(result.throwable)
            }
        }
    }
}