package ir.miare.androidcodechallenge.core.domain

import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import ir.miare.androidcodechallenge.core.model.SortOption
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedLeagueDisplayItemsUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository
) {
    operator fun invoke(sortOption: SortOption): Flow<ApiResult<List<LeagueDisplayItem>>> {
        return leagueRepository.getHome()
            .map { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val displayItemsFlow = result.data.let { fakeDataList ->
                            val allPlayerLeagues = fakeDataList.flatMap { fakeData ->
                                fakeData.players.map { player -> player to fakeData.league }
                            }

                            when (sortOption) {
                                SortOption.TEAM_AND_LEAGUE_RANK -> {
                                    val sortedLeagues = fakeDataList.sortedBy { it.league.rank }
                                    sortedLeagues.flatMap { leagueData ->
                                        val league = leagueData.league
                                        val sortedPlayers = leagueData.players
                                            .sortedBy { it.team.rank }
                                            .map { player ->
                                                LeagueDisplayItem.PlayerItem(player, league, null)
                                            }
                                        listOf(LeagueDisplayItem.Header(league)) + sortedPlayers
                                    }
                                }
                                SortOption.MOST_GOALS -> {
                                    allPlayerLeagues
                                        .sortedByDescending { (player, _) -> player.totalGoal }
                                        .map { (player, league) ->
                                            LeagueDisplayItem.PlayerItem(player, league, null)
                                        }
                                }
                                SortOption.AVERAGE_GOALS_PER_MATCH -> {
                                    val leagueAverages = fakeDataList.map { leagueData ->
                                        val league = leagueData.league
                                        val totalGoals = leagueData.players.sumOf { it.totalGoal }
                                        val avgGoalsPerMatch = if (league.totalMatches > 0) {
                                            totalGoals.toFloat() / league.totalMatches
                                        } else {
                                            0f
                                        }
                                        league to avgGoalsPerMatch
                                    }
                                    leagueAverages
                                        .sortedByDescending { (_, avg) -> avg }
                                        .map { (league, _) -> LeagueDisplayItem.Header(league) }
                                }
                                SortOption.NONE -> {
                                    fakeDataList.flatMap { leagueData ->
                                        val league = leagueData.league
                                        val playerItems = leagueData.players.map { player ->
                                            LeagueDisplayItem.PlayerItem(player, league, null)
                                        }
                                        listOf(LeagueDisplayItem.Header(league)) + playerItems
                                    }
                                }
                            }
                        }
                        ApiResult.Success(displayItemsFlow)
                    }
                    is ApiResult.Error -> result
                }
            }
            .catch { e ->
                emit(ApiResult.Error(e))
            }
    }
}