package ir.miare.androidcodechallenge.domain.repository

import ir.miare.androidcodechallenge.data.db.FollowedPlayerDao
import ir.miare.androidcodechallenge.data.db.FollowedPlayerEntity
import ir.miare.androidcodechallenge.data.model.League
import ir.miare.androidcodechallenge.data.model.Player
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FollowedPlayerRepository @Inject constructor(
    private val dao: FollowedPlayerDao
) {

    fun getFollowedPlayers(): Flow<List<FollowedPlayerEntity>> =
        dao.getFollowedPlayers()

    suspend fun follow(player: Player, league: League) {
        dao.insert(
            FollowedPlayerEntity(
                playerName = player.name,
                totalGoal = player.totalGoal,
                teamName = player.team.name,
                teamRank = player.team.rank,
                leagueName = league.name,
                leagueCountry = league.country,
                leagueRank = league.rank,
                leagueTotalMatches = league.totalMatches
            )
        )
    }

    suspend fun unfollow(playerName: String) {
        dao.deleteByName(playerName)
    }

    suspend fun isFollowed(playerName: String): Boolean {
        return dao.isFollowed(playerName)
    }
}
