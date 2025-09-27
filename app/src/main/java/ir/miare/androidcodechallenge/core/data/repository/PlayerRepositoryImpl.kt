package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.database.dao.PlayerDao
import ir.miare.androidcodechallenge.core.model.PlayerWithDetails
import ir.miare.androidcodechallenge.core.model.SortMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerDao: PlayerDao,
    private val sortSettings: SortPreferencesRepository,
) : PlayerRepository {

    override val sortMode: Flow<SortMode> = sortSettings.sortMode
    override suspend fun setSortMode(mode: SortMode) = sortSettings.setSortMode(mode)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun players(pageSize: Int, offset: Int): Flow<List<PlayerWithDetails>> {
        return sortMode
            .flatMapLatest { mode ->
                when (mode) {
                    SortMode.GOALS_SCORED -> playerDao.getPlayersSortedByGoals(pageSize, offset)
                    SortMode.LEAGUE_RANK -> playerDao.getPlayersSortedByLeagueRank(pageSize, offset)
                    SortMode.TEAM_RANK -> playerDao.getPlayersSortedByTeamRank(pageSize, offset)
                    SortMode.DEFAULT, SortMode.LEAGUE_GOAL_AVG -> playerDao.getPlayersDefaultPaged(
                        pageSize,
                        offset
                    )
                }
            }
    }

    override fun followedPlayers(): Flow<List<PlayerWithDetails>> = playerDao.getFollowedPlayers()

    override suspend fun setPlayerFollowed(playerId: String, isFollowed: Boolean) =
        playerDao.updatePlayerFollowStatus(playerId, isFollowed)
}
