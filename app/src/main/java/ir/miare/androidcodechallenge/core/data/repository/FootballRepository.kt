package ir.miare.androidcodechallenge.core.data.repository

import ir.miare.androidcodechallenge.core.data.helper.FileReader
import ir.miare.androidcodechallenge.core.database.dao.LeagueDao
import ir.miare.androidcodechallenge.core.database.dao.PlayerDao
import ir.miare.androidcodechallenge.core.database.dao.TeamDao
import ir.miare.androidcodechallenge.core.database.model.LeagueEntity
import ir.miare.androidcodechallenge.core.database.model.PlayerEntity
import ir.miare.androidcodechallenge.core.database.model.TeamEntity
import ir.miare.androidcodechallenge.core.model.Competition
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface FootballRepository {
    suspend fun ensureSeeded()
}

class FootballRepositoryImpl @Inject constructor(
    private val leagueDao: LeagueDao,
    private val playerDao: PlayerDao,
    private val teamDao: TeamDao,
    private val fileReader: FileReader,
) : FootballRepository {
    override suspend fun ensureSeeded() {
        if (playerDao.getAllPlayers().isNotEmpty()) return

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
                )
            )
            item.players.forEach { player ->
                val teamId = player.team.name
                teamEntities.add(
                    TeamEntity(
                        teamId = teamId,
                        teamName = player.team.name,
                        rank = player.team.rank,
                        leagueId = leagueId
                    )
                )
                playerEntities.add(
                    PlayerEntity(
                        playerId = player.name,
                        playerName = player.name,
                        goalsScored = player.totalGoal,
                        teamId = teamId,
                    )
                )
            }
        }

        leagueDao.insertAll(leagueEntities.distinctBy { it.leagueId })
        teamDao.insertAll(teamEntities.distinctBy { it.teamId })
        playerDao.insertAll(playerEntities)
    }
}
