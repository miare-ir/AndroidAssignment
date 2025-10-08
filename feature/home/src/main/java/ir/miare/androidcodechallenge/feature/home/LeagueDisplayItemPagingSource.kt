package ir.miare.androidcodechallenge.feature.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem

class LeagueDisplayItemPagingSource(
    private val items: List<LeagueDisplayItem>
) : PagingSource<Int, LeagueDisplayItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LeagueDisplayItem> {
        val page = params.key ?: 0
        val start = page * params.loadSize
        val end = kotlin.math.min(start + params.loadSize, items.size)
        val data = if (start < end) items.subList(start, end) else emptyList()
        val prevKey = if (page > 0) page - 1 else null
        val nextKey = if (end < items.size) page + 1 else null
        return LoadResult.Page(data, prevKey, nextKey)
    }

    override fun getRefreshKey(state: PagingState<Int, LeagueDisplayItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}