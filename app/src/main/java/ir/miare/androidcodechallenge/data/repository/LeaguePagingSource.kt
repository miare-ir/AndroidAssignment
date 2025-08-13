package ir.miare.androidcodechallenge.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.data.remote.ApiService
import ir.miare.androidcodechallenge.presentation.league_data_list.RankingSort

class LeaguePagingSource(
    private val apiService: ApiService,
    private val pageSize: Int,
    private val sort: RankingSort
) : PagingSource<Int, LeagueData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LeagueData> {
        return try {
            val allLeagues = apiService.getLeagueData()

            val sortedLeagues = when (sort) {
                RankingSort.None -> allLeagues
                RankingSort.ByTeam -> allLeagues.map { league ->
                    league.copy(players = league.players.sortedBy { it.team.name })
                }

                RankingSort.ByLeagueRanking -> allLeagues.sortedBy { it.league.rank }
                RankingSort.ByMostGoals -> allLeagues.map { league ->
                    league.copy(players = league.players.sortedByDescending { it.totalGoal })
                }

                RankingSort.ByAverageGoalsPerMatch -> allLeagues.sortedByDescending { league ->
                    val totalGoals = league.players.sumOf { it.totalGoal }
                    val totalMatches = league.league.totalMatches
                    if (totalMatches > 0) totalGoals.toDouble() / totalMatches else 0.0
                }
            }

            val page = params.key ?: 0
            val fromIndex = page * pageSize
            val toIndex = minOf(fromIndex + pageSize, sortedLeagues.size)
            val pagedData = if (fromIndex < sortedLeagues.size) sortedLeagues.subList(
                fromIndex,
                toIndex
            ) else emptyList()

            LoadResult.Page(
                data = pagedData,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (toIndex < sortedLeagues.size) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LeagueData>): Int? {
        return state.anchorPosition?.let { anchor ->
            anchor / pageSize
        }
    }
}
