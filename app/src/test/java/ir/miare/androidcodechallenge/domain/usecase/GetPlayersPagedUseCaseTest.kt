package ir.miare.androidcodechallenge.domain.usecase

import androidx.paging.PagingData
import androidx.paging.PagingSource
import ir.miare.androidcodechallenge.data.paging.PlayersPagingSource
import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.domain.entity.Team
import ir.miare.androidcodechallenge.domain.entity.League
import ir.miare.androidcodechallenge.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.first
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

@RunWith(MockitoJUnitRunner::class)
class GetPlayersPagedUseCaseTest {

    @Mock
    private lateinit var mockRepository: PlayersRepository

    private lateinit var getPlayersPagedUseCase: GetPlayersPagedUseCase

    @Before
    fun setup() {
        getPlayersPagedUseCase = GetPlayersPagedUseCase(mockRepository)
    }

    @Test
    fun `invoke without followedSet should return paging flow`() = runTest {
        val pageSize = 20
        val sortMode = SortMode.ALPHABETICAL

        val result = getPlayersPagedUseCase.invoke(pageSize, sortMode)

        assertNotNull(result)
    }

    @Test
    fun `invoke with followedSet should return paging flow`() = runTest {
        val pageSize = 20
        val sortMode = SortMode.MOST_GOALS
        val followedSet = setOf("Jude Bellingham", "Robert Lewandowski")

        val result = getPlayersPagedUseCase.invoke(pageSize, sortMode, followedSet)

        assertNotNull(result)
    }

    @Test
    fun `SortMode enum should have correct values`() {
        assertEquals(3, SortMode.values().size)
        assertTrue(SortMode.values().contains(SortMode.ALPHABETICAL))
        assertTrue(SortMode.values().contains(SortMode.MOST_GOALS))
        assertTrue(SortMode.values().contains(SortMode.TEAM_AND_LEAGUE_RANK))
    }
}
