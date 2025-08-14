package ir.miare.androidcodechallenge.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.data.paging.PlayersPagingSource
import ir.miare.androidcodechallenge.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

enum class SortMode {
    ALPHABETICAL,
    TEAM_AND_LEAGUE_RANK,
    MOST_GOALS
}

class GetPlayersPagedUseCase @Inject constructor(
    private val repository: PlayersRepository
) {
    fun invoke(pageSize: Int, sortMode: SortMode): Flow<PagingData<Player>> {
        return Pager(PagingConfig(pageSize = pageSize)) {
            PlayersPagingSource(repository, sortMode)
        }.flow
    }

    fun invoke(pageSize: Int, sortMode: SortMode, followedSet: Set<String>): Flow<PagingData<Player>> {
        return Pager(PagingConfig(pageSize = pageSize)) {
            PlayersPagingSource(repository, sortMode, followedSet)
        }.flow
    }
}


