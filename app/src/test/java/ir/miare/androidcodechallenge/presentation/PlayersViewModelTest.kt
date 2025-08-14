package ir.miare.androidcodechallenge.presentation

import androidx.paging.PagingData
import androidx.paging.PagingSource
import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.domain.entity.Team
import ir.miare.androidcodechallenge.domain.entity.League
import ir.miare.androidcodechallenge.domain.usecase.GetPlayersPagedUseCase
import ir.miare.androidcodechallenge.domain.usecase.ObserveFollowedUseCase
import ir.miare.androidcodechallenge.domain.usecase.SortMode
import ir.miare.androidcodechallenge.domain.usecase.ToggleFollowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PlayersViewModelTest {

    @Mock
    private lateinit var mockGetPlayersPaged: GetPlayersPagedUseCase

    @Mock
    private lateinit var mockObserveFollowed: ObserveFollowedUseCase

    @Mock
    private lateinit var mockToggleFollow: ToggleFollowUseCase

    private lateinit var playersViewModel: PlayersViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        `when`(mockObserveFollowed()).thenReturn(flowOf(emptySet<String>()))
        `when`(mockGetPlayersPaged.invoke(10, SortMode.ALPHABETICAL)).thenReturn(flowOf(PagingData.empty()))
        `when`(mockGetPlayersPaged.invoke(10, SortMode.MOST_GOALS)).thenReturn(flowOf(PagingData.empty()))
        
        playersViewModel = PlayersViewModel(
            mockGetPlayersPaged,
            mockObserveFollowed,
            mockToggleFollow
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `sortMode should have default value`() {
        assertEquals(SortMode.ALPHABETICAL, playersViewModel.sortMode.value)
    }

    @Test
    fun `setSortMode should update sort mode`() = runTest {
        val newSortMode = SortMode.MOST_GOALS

        playersViewModel.setSortMode(newSortMode)

        assertEquals(newSortMode, playersViewModel.sortMode.value)
    }

    @Test
    fun `onToggleFollow should call toggle follow use case`() = runTest {
        val playerName = "Jude Bellingham"
        `when`(mockToggleFollow.invoke(playerName)).thenReturn(Unit)

        playersViewModel.onToggleFollow(playerName)
        
        testDispatcher.scheduler.advanceUntilIdle()

        verify(mockToggleFollow).invoke(playerName)
    }
}
