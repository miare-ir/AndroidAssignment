package ir.miare.androidcodechallenge.data.repository

import ir.miare.androidcodechallenge.data.remote.ApiService
import ir.miare.androidcodechallenge.data.remote.FakeDataResponse
import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.domain.entity.Team
import ir.miare.androidcodechallenge.domain.entity.League
import ir.miare.androidcodechallenge.domain.repository.PlayersRepository
import javax.inject.Inject

class PlayersRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PlayersRepository {
    
    override suspend fun fetchAllLeagues(): List<League> {
        return apiService.getData().map { fakeData ->
            League(
                name = fakeData.league.name,
                country = fakeData.league.country,
                rank = fakeData.league.rank,
                totalMatches = fakeData.league.totalMatches
            )
        }
    }

    override suspend fun fetchAllPlayers(): List<Player> {
        return apiService.getData().flatMap { fakeData ->
            val league = League(
                name = fakeData.league.name,
                country = fakeData.league.country,
                rank = fakeData.league.rank,
                totalMatches = fakeData.league.totalMatches
            )
            fakeData.players.map { fakePlayer ->
                Player(
                    name = fakePlayer.name,
                    team = Team(
                        name = fakePlayer.team.name,
                        rank = fakePlayer.team.rank
                    ),
                    league = league,
                    totalGoal = fakePlayer.totalGoal
                )
            }
        }
    }
}


