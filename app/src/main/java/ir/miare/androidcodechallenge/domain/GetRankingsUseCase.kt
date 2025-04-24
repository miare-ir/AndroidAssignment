package ir.miare.androidcodechallenge.domain

import ir.miare.androidcodechallenge.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRankingsUseCase @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(): Flow<Resource<List<FakeData>>> = repository.getRankings()
}