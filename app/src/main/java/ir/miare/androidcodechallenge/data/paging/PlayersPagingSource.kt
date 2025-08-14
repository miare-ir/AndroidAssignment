package ir.miare.androidcodechallenge.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.domain.repository.PlayersRepository
import ir.miare.androidcodechallenge.domain.usecase.SortMode

class PlayersPagingSource(
    private val repository: PlayersRepository,
    private val sortMode: SortMode,
    private val followedSet: Set<String>? = null
) : PagingSource<Int, Player>() {

    companion object {
        private var globalCachedList: List<Player>? = null
        private var lastFetchTime: Long = 0
        private const val CACHE_DURATION_MS = 5 * 60 * 1000
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        return try {
            val page = params.key ?: 0

            val currentTime = System.currentTimeMillis()
            val shouldFetch = globalCachedList == null || 
                            (currentTime - lastFetchTime) > CACHE_DURATION_MS

            if (shouldFetch) {
                val allPlayers = repository.fetchAllPlayers()
                globalCachedList = allPlayers
                lastFetchTime = currentTime
            }

            val allPlayers = globalCachedList ?: emptyList()

            val sorted = when (sortMode) {
                SortMode.ALPHABETICAL -> allPlayers.sortedBy { it.name }
                SortMode.MOST_GOALS -> allPlayers.sortedByDescending { it.totalGoal }
                SortMode.TEAM_AND_LEAGUE_RANK -> allPlayers.sortedWith(
                    compareBy<Player> { it.league.rank }
                        .thenBy { it.team.rank }
                )
            }

            val filtered = if (followedSet != null) {
                sorted.filter { player -> player.name in followedSet }
            } else {
                sorted
            }

            val finalList = filtered.distinctBy { "${it.name}_${it.team.name}" }

            val from = page * params.loadSize
            val to = minOf(finalList.size, from + params.loadSize)
            val pageData = if (from < to) finalList.subList(from, to) else emptyList()

            LoadResult.Page(
                data = pageData,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (to >= finalList.size) null else page + 1
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
