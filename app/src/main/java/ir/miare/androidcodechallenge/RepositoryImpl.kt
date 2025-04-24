package ir.miare.androidcodechallenge

import ir.miare.androidcodechallenge.data.Api
import ir.miare.androidcodechallenge.data.toFakeDate
import ir.miare.androidcodechallenge.domain.FakeData
import ir.miare.androidcodechallenge.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api,
) : Repository {
    override fun getRankings(): Flow<Resource<List<FakeData>>> = flow {
        try {
            val result = api.getRankings().map { it.toFakeDate() }
            emit(Resource.Success(data = result))
        } catch (t: Throwable) {
            emit(Resource.Error(message = "Something wrong"))
        }
    }
}