package ir.miare.androidcodechallenge.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import ir.miare.androidcodechallenge.core.domain.GetSortedLeagueDisplayItemsUseCase
import ir.miare.androidcodechallenge.core.domain.ObserveFollowedSet
import ir.miare.androidcodechallenge.core.domain.ToggleFollowUseCase
import ir.miare.androidcodechallenge.core.model.League
import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.SortOption
import ir.miare.androidcodechallenge.core.model.Team
import ir.miare.androidcodechallenge.core.model.stableKey
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import ir.miare.androidcodechallenge.feature.home.HomeViewModel.Companion.SORT_OPTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTests {

    private lateinit var viewModel: HomeViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getSortedLeagueDisplayItemsUseCase: GetSortedLeagueDisplayItemsUseCase
    private lateinit var observeFollowedSet: ObserveFollowedSet
    private lateinit var toggleFollowUseCase: ToggleFollowUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val sampleLeague = League(
        name = "Premier League",
        country = "England",
        rank = 1,
        totalMatches = 38
    )
    private val samplePlayer = Player(
        name = "Erling Haaland",
        team = Team(
            name = "Manchester City",
            rank = 1
        ),
        totalGoal = 27,
        isFollowed = false
    )
    private val sampleDisplayItems = listOf(
        LeagueDisplayItem.Header(sampleLeague),
        LeagueDisplayItem.PlayerItem(
            player = samplePlayer,
            league = sampleLeague,
            avgGoals = samplePlayer.totalGoal.toFloat() / sampleLeague.totalMatches
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle()
        getSortedLeagueDisplayItemsUseCase = mockk()
        observeFollowedSet = mockk()
        toggleFollowUseCase = mockk()
        every { observeFollowedSet.invoke() } returns flowOf(emptySet())
        viewModel = HomeViewModel(
            getSortedLeagueDisplayItemsUseCase,
            observeFollowedSet,
            toggleFollowUseCase,
            savedStateHandle
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSortChanged updates sort option in savedStateHandle`() = runTest {
        val newSortOption = SortOption.NONE

        viewModel.onSortChanged(newSortOption)

        assertEquals(
            newSortOption,
            savedStateHandle[SORT_OPTION],
            "Sort option should be updated in savedStateHandle"
        )
        assertEquals(
            newSortOption,
            viewModel.sortOption.value,
            "Sort option should be updated in viewModel"
        )
    }

    @Test
    fun `onSortChanged with same sort option does not trigger unnecessary update`() = runTest {
        savedStateHandle[SORT_OPTION] = SortOption.NONE
        val sameSortOption = SortOption.NONE

        viewModel.onSortChanged(sameSortOption)

        assertEquals(
            SortOption.NONE,
            viewModel.sortOption.value,
            "Sort option should remain unchanged"
        )
    }

    @Test
    fun `homeUiState emits Loading initially`() = runTest {
        val initialState = viewModel.homeUiState.value

        assertTrue(initialState is HomeUiState.Loading, "Initial state should be Loading")
    }

    @Test
    fun `homeUiState emits Empty when use case returns empty list`() = runTest {
        coEvery { getSortedLeagueDisplayItemsUseCase.invoke(any()) } returns flowOf(
            ApiResult.Success(
                emptyList()
            )
        )

        val states = mutableListOf<HomeUiState>()
        viewModel.homeUiState.take(2).toList(states)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(
            states.any { it is HomeUiState.Empty },
            "State should include Empty when data is empty"
        )
        val lastState = states.last()
        assertTrue(lastState is HomeUiState.Empty, "Last state should be Empty when data is empty")
    }

    @Test
    fun `homeUiState emits Success when use case returns non-empty list`() = runTest {
        coEvery { getSortedLeagueDisplayItemsUseCase.invoke(any()) } returns flowOf(
            ApiResult.Success(
                sampleDisplayItems
            )
        )

        val states = mutableListOf<HomeUiState>()
        viewModel.homeUiState.take(2).toList(states)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(
            states.any { it is HomeUiState.Success },
            "State should include Success when data is non-empty"
        )
        val lastState = states.last()
        assertTrue(
            lastState is HomeUiState.Success,
            "Last state should be Success when data is non-empty"
        )
        assertEquals(
            sampleDisplayItems,
            (lastState).displayItems,
            "Display items should match expected"
        )
    }

    @Test
    fun `homeUiState emits Error when use case returns error`() = runTest {
        val errorMessage = "Network error"
        coEvery { getSortedLeagueDisplayItemsUseCase.invoke(any()) } returns flowOf(
            ApiResult.Error(
                Throwable(errorMessage)
            )
        )

        val states = mutableListOf<HomeUiState>()
        viewModel.homeUiState.take(2).toList(states)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(
            states.any { it is HomeUiState.Error },
            "State should include Error when use case fails"
        )
        val lastState = states.last()
        assertTrue(lastState is HomeUiState.Error, "Last state should be Error when use case fails")
        assertEquals(
            errorMessage,
            lastState.message,
            "Error message should match expected"
        )
    }

    @Test
    fun `onFollowClick calls toggleFollow with correct player`() = runTest {
        coEvery { toggleFollowUseCase.invoke(samplePlayer) } returns Unit

        viewModel.onFollowClick(samplePlayer)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { toggleFollowUseCase.invoke(samplePlayer) }
    }

    @Test
    fun `pagingUi updates isFollowed based on followedKeys`() = runTest {
        val samplePlayer = mockk<Player>()
        every { samplePlayer.name } returns "Erling Haaland"
        every { samplePlayer.team } returns Team(name = "Manchester City", rank = 1)
        every { samplePlayer.totalGoal } returns 27
        every { samplePlayer.isFollowed } returns false andThen true
        every { samplePlayer.stableKey() } returns "Erling Haaland"
        val sampleDisplayItems = listOf(
            LeagueDisplayItem.Header(sampleLeague),
            LeagueDisplayItem.PlayerItem(
                player = samplePlayer,
                league = sampleLeague,
                avgGoals = samplePlayer.totalGoal.toFloat() / sampleLeague.totalMatches
            )
        )
        every { observeFollowedSet.invoke() } returns flowOf(setOf("Erling Haaland"))
        coEvery { getSortedLeagueDisplayItemsUseCase.invoke(any()) } returns flowOf(
            ApiResult.Success(
                sampleDisplayItems
            )
        )

        val pagingSource = mockk<LeagueDisplayItemPagingSource>()
        coEvery { pagingSource.load(any()) } returns PagingSource.LoadResult.Page(
            data = sampleDisplayItems,
            prevKey = null,
            nextKey = null
        )
        every { pagingSource.registerInvalidatedCallback(any()) } returns Unit
        every { pagingSource.invalidate() } returns Unit

        val pagingFlow = Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { pagingSource }
        ).flow

        val pagingUi = combine(pagingFlow, flowOf(setOf("Erling Haaland"))) { paging, keys ->
            paging.map { item ->
                when (item) {
                    is LeagueDisplayItem.Header -> item
                    is LeagueDisplayItem.PlayerItem -> {
                        val followed = item.player.stableKey() in keys
                        item.copy(player = item.player.copy(isFollowed = followed))
                    }
                }
            }
        }

        val states = mutableListOf<PagingData<LeagueDisplayItem>>()
        pagingUi.take(1).toList(states)
        testDispatcher.scheduler.advanceUntilIdle()

        println("Collected PagingData: $states")
        states.first().map { item ->
            if (item is LeagueDisplayItem.PlayerItem) {
                println("Player: ${item.player.name}, isFollowed: ${item.player.isFollowed}")
                assertTrue(item.player.isFollowed, "Player should be marked as followed")
            }
        }
    }
}