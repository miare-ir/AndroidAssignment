package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.data.helper.FileReader
import ir.miare.androidcodechallenge.core.data.helper.SortSettings
import ir.miare.androidcodechallenge.core.database.dao.LeagueDao
import ir.miare.androidcodechallenge.core.database.dao.PlayerDao
import ir.miare.androidcodechallenge.core.database.dao.TeamDao
import ir.miare.androidcodechallenge.core.database.model.LeagueEntity
import ir.miare.androidcodechallenge.core.database.model.PlayerEntity
import ir.miare.androidcodechallenge.core.database.model.TeamEntity
import ir.miare.androidcodechallenge.core.model.Competition
import ir.miare.androidcodechallenge.core.model.PlayerWithDetails
import ir.miare.androidcodechallenge.core.model.SortMode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject


class FootballRepositoryImpl @Inject constructor(
    private val leagueDao: LeagueDao,
    private val playerDao: PlayerDao,
    private val teamDao: TeamDao,
    private val fileReader: FileReader,
    private val sortSettings: SortSettings,
) : FootballRepository {

    override val sortMode: Flow<SortMode> = sortSettings.sortMode

    override suspend fun setSortMode(mode: SortMode) = sortSettings.setSortMode(mode)

    override suspend fun ensureSeeded() {
        val count = playerDao.countPlayers()
        if (count > 0) return

        val json = fileReader.readeJsonFile()

        val seedItems: List<Competition> = Json.decodeFromString(json)
        val leagueEntities = mutableListOf<LeagueEntity>()
        val playerEntities = mutableListOf<PlayerEntity>()
        val teamEntities = mutableListOf<TeamEntity>()

        seedItems.forEach { item ->
            val leagueId = item.league.name
            leagueEntities.add(
                LeagueEntity(
                    leagueId = leagueId,
                    leagueName = item.league.name,
                    country = item.league.country,
                    rank = item.league.rank,
                    totalMatches = item.league.totalMatches,
                    imageUrl = item.league.imageUrl,
                )
            )
            item.players.forEach { player ->
                val teamId = player.team.name
                teamEntities.add(
                    TeamEntity(
                        teamId = teamId,
                        teamName = player.team.name,
                        rank = player.team.rank,
                        leagueId = leagueId,
                        imageUrl = player.team.imageUrl,
                    )
                )
                playerEntities.add(
                    PlayerEntity(
                        playerId = UUID.randomUUID().toString(),
                        playerName = player.name,
                        goalsScored = player.totalGoal,
                        teamId = teamId,
                        imageUrl = player.imageUrl,
                    )
                )
            }
        }

        leagueDao.insertAll(leagueEntities.distinctBy { it.leagueId })
        teamDao.insertAll(teamEntities.distinctBy { it.teamId })
        playerDao.insertAll(playerEntities)
    }

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
