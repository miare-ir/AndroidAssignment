package ir.miare.androidcodechallenge.domain

import ir.miare.androidcodechallenge.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getRankings(): Flow<Resource<List<FakeData>>>
}