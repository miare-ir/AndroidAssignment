package ir.miare.androidcodechallenge.feature.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import kotlin.math.min

class LeagueDisplayItemPagingSource(
    private val items: List<LeagueDisplayItem>
) : PagingSource<Int, LeagueDisplayItem>() {
    override fun getRefreshKey(state: PagingState<Int, LeagueDisplayItem>): Int? {
        val anchor = state.anchorPosition ?: return null
        val closest = state.closestPageToPosition(anchor)
        return closest?.prevKey?.plus(1) ?: closest?.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LeagueDisplayItem> {
        val page = params.key ?: 0
        val from = page * params.loadSize
        val to = min(from + params.loadSize, items.size)
        val slice = if (from < to) items.subList(from, to) else emptyList()

        val prev = if (page == 0) null else page - 1
        val next = if (to >= items.size) null else page + 1
        return LoadResult.Page(slice, prevKey = prev, nextKey = next)
    }
}