package ir.miare.androidcodechallenge.core.domain

import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import ir.miare.androidcodechallenge.core.model.SortOption
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSortedLeagueDisplayItemsUseCase @Inject constructor(
    private val leagueRepository: LeagueRepository
) {
    suspend operator fun invoke(sortOption: SortOption): ApiResult<Flow<List<LeagueDisplayItem>>> {
        return when (val result = leagueRepository.getHome()) {
            is ApiResult.Success -> {
                val displayItemsFlow = result.data.map { fakeDataList ->
                    val allPlayerLeagues = fakeDataList.flatMap { fakeData ->
                        fakeData.players.map { player -> player to fakeData.league }
                    }

                    val sortedPlayerLeagues = when (sortOption) {
                        SortOption.TEAM_AND_LEAGUE_RANK ->
                            allPlayerLeagues.sortedBy { (player, league) ->
                                player.team.rank + league.rank
                            }
                        SortOption.MOST_GOALS ->
                            allPlayerLeagues.sortedByDescending { (player, _) ->
                                player.totalGoal
                            }
                        SortOption.AVERAGE_GOALS_PER_MATCH ->
                            allPlayerLeagues.sortedByDescending { (player, league) ->
                                player.totalGoal.toFloat() / league.totalMatches
                            }
                        SortOption.NONE ->
                            allPlayerLeagues
                    }

                    sortedPlayerLeagues
                        .groupBy { (_, league) -> league.name }
                        .map { (_, playerLeagues) ->
                            val league = playerLeagues.first().second
                            val playerItems = playerLeagues.map { (player, league) ->
                                val avgGoals =
                                    if (sortOption == SortOption.AVERAGE_GOALS_PER_MATCH) {
                                        player.totalGoal.toFloat() / league.totalMatches
                                    } else {
                                        null
                                    }
                                LeagueDisplayItem.PlayerItem(player, league, avgGoals)
                            }
                            listOf(LeagueDisplayItem.Header(league)) + playerItems
                        }
                        .flatten()
                }
                ApiResult.Success(displayItemsFlow)
            }

            is ApiResult.Error -> ApiResult.Error(result.throwable)
        }
    }
}