package ir.miare.androidcodechallenge.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.miare.androidcodechallenge.data.model.LeagueData
import ir.miare.androidcodechallenge.data.remote.ApiService

class LeaguePagingSource(
    private val apiService: ApiService,
    private val pageSize: Int = 5
) : PagingSource<Int, LeagueData>() {

    private var fullList: List<LeagueData>? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LeagueData> {
        return try {
            val page = params.key ?: 1

            if (fullList.isNullOrEmpty()) {
                fullList = apiService.getLeagueData() // full fetch once
            }

            val start = (page - 1) * pageSize
            val end = minOf(start + pageSize, fullList!!.size)
            val sublist = if (start < fullList!!.size) {
                fullList!!.subList(start, end)
            } else {
                emptyList()
            }

            LoadResult.Page(
                data = sublist,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (end < fullList!!.size) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LeagueData>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
