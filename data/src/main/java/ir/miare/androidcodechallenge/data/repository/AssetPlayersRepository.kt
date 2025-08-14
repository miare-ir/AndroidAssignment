package ir.miare.androidcodechallenge.data.repository

import android.content.Context
import kotlinx.serialization.json.Json
import ir.miare.androidcodechallenge.data.remote.FakeData
import ir.miare.androidcodechallenge.domain.model.PlayerId
import ir.miare.androidcodechallenge.domain.model.PlayerWithContext
import ir.miare.androidcodechallenge.domain.repo.PlayersRepository
import java.security.MessageDigest

class AssetPlayersRepository(
    private val context: Context,
    private val json: Json
) : PlayersRepository {

    override suspend fun getAllPlayers(): List<PlayerWithContext> {
        val input = context.assets.open("data.json").use { it.readBytes() }
        val leagues: List<FakeData> = json.decodeFromString(input.decodeToString())
        return leagues.flatMap { league ->
            league.players.map { player ->
                PlayerWithContext(
                    id = PlayerId(stableId("${player.name}:${player.team.name}")),
                    name = player.name,
                    totalGoals = player.totalGoal,
                    teamName = player.team.name,
                    teamRank = player.team.rank,
                    leagueName = league.league.name,
                    leagueCountry = league.league.country,
                    leagueRank = league.league.rank,
                    leagueTotalMatches = league.league.totalMatches
                )
            }
        }
    }

    private fun stableId(text: String): String {
        val md = MessageDigest.getInstance("SHA-1")
        val bytes = md.digest(text.toByteArray())
        return bytes.joinToString(separator = "") { b ->
            ((b.toInt() and 0xFF) + 0x100).toString(16).substring(1)
        }
    }
}
