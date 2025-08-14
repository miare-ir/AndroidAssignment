package ir.miare.androidcodechallenge.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.domain.entity.Team
import ir.miare.androidcodechallenge.domain.entity.League
import ir.miare.androidcodechallenge.domain.repository.PlayersRepository
import ir.miare.androidcodechallenge.domain.usecase.SortMode
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import java.lang.reflect.Field

@RunWith(MockitoJUnitRunner::class)
class PlayersPagingSourceTest {

    @Mock
    private lateinit var mockRepository: PlayersRepository

    private lateinit var playersPagingSource: PlayersPagingSource

    private val testPlayers = listOf(
        Player("Jude Bellingham", Team("Real Madrid", 1), League("La Liga", "Spain", 2, 38), 19),
        Player("Robert Lewandowski", Team("Barcelona", 2), League("La Liga", "Spain", 2, 38), 23)
    )

    @Before
    fun setup() {
        clearPagingSourceCache()
        playersPagingSource = PlayersPagingSource(mockRepository, SortMode.ALPHABETICAL)
    }

    private fun clearPagingSourceCache() {
        try {
            val globalCachedListField = PlayersPagingSource::class.java.getDeclaredField("globalCachedList")
            globalCachedListField.isAccessible = true
            globalCachedListField.set(null, null)
            
            val lastFetchTimeField = PlayersPagingSource::class.java.getDeclaredField("lastFetchTime")
            lastFetchTimeField.isAccessible = true
            lastFetchTimeField.set(null, 0L)
        } catch (e: Exception) {
            // Ignore reflection errors
        }
    }

    @Test
    fun `load should return first page with correct data`() = runTest {
        val pageSize = 1
        val loadParams = PagingSource.LoadParams.Refresh<Int>(
            key = 0,
            loadSize = pageSize,
            placeholdersEnabled = false
        )
        `when`(mockRepository.fetchAllPlayers()).thenReturn(testPlayers)

        val result = playersPagingSource.load(loadParams)

        assertTrue(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(1, pageResult.data.size)
        assertEquals(null, pageResult.prevKey)
        assertEquals(1, pageResult.nextKey)
    }

    @Test
    fun `load should handle empty players list`() = runTest {
        val pageSize = 2
        val loadParams = PagingSource.LoadParams.Refresh<Int>(
            key = 0,
            loadSize = pageSize,
            placeholdersEnabled = false
        )
        `when`(mockRepository.fetchAllPlayers()).thenReturn(emptyList())

        val result = playersPagingSource.load(loadParams)

        assertTrue(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(0, pageResult.data.size)
        assertEquals(null, pageResult.prevKey)
        assertEquals(null, pageResult.nextKey)
    }
}
