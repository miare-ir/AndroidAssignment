package ir.miare.androidcodechallenge.feature.followedplayers

import androidx.lifecycle.viewModelScope
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import ir.miare.androidcodechallenge.core.domain.GetFollowedPlayersUseCase
import ir.miare.androidcodechallenge.core.domain.UnFollowPlayerUseCase
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
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
class FollowedPlayersViewModelTests {

    private lateinit var viewModel: FollowedPlayersViewModel
    private lateinit var getFollowedPlayersUseCase: GetFollowedPlayersUseCase
    private lateinit var unFollowPlayerUseCase: UnFollowPlayerUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val samplePlayer = Player(
        name = "Erling Haaland",
        team = Team(
            name = "Manchester City",
            rank = 1
        ),
        totalGoal = 27,
        isFollowed = true
    )
    private val samplePlayers = listOf(samplePlayer)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getFollowedPlayersUseCase = mockk()
        unFollowPlayerUseCase = mockk()
        coEvery { getFollowedPlayersUseCase() } returns flowOf(emptyList())
        viewModel = FollowedPlayersViewModel(
            getFollowedPlayersUseCase,
            unFollowPlayerUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits Loading initially`() = runTest {
        val initialState = viewModel.uiState.value

        assertTrue(
            initialState is FollowedPlayersUiState.Loading,
            "Initial state should be Loading"
        )
    }

    @Test
    fun `uiState emits Empty when use case returns empty list`() = runTest {
        coEvery { getFollowedPlayersUseCase() } returns flowOf(emptyList())

        val states = mutableListOf<FollowedPlayersUiState>()
        viewModel.uiState.take(2).toList(states)
        testDispatcher.scheduler.advanceUntilIdle()

        println("Collected states: $states")
        assertTrue(
            states.any { it is FollowedPlayersUiState.Empty },
            "State should include Empty when data is empty"
        )
        val lastState = states.last()
        assertTrue(
            lastState is FollowedPlayersUiState.Empty,
            "Last state should be Empty when data is empty"
        )
    }

    @Test
    fun `uiState emits Success when use case returns non-empty list`() = runTest {
        getFollowedPlayersUseCase = mockk()
        unFollowPlayerUseCase = mockk()
        coEvery { getFollowedPlayersUseCase() } returns flowOf(samplePlayers)
        viewModel = FollowedPlayersViewModel(getFollowedPlayersUseCase, unFollowPlayerUseCase)

        testDispatcher.scheduler.advanceTimeBy(5_000)
        testDispatcher.scheduler.advanceUntilIdle()
        val states = mutableListOf<FollowedPlayersUiState>()
        viewModel.uiState.take(2).toList(states)

        viewModel.viewModelScope.cancel()

        println("Collected states: $states")
        assertTrue(
            states.any { it is FollowedPlayersUiState.Success },
            "State should include Success when data is non-empty"
        )
        val lastState = states.last()
        assertTrue(
            lastState is FollowedPlayersUiState.Success,
            "Last state should be Success when data is non-empty"
        )
        assertEquals(
            samplePlayers,
            lastState.players,
            "Players should match expected"
        )
    }

    @Test
    fun `uiState emits Error when use case throws exception`() = runTest {
        getFollowedPlayersUseCase = mockk()
        unFollowPlayerUseCase = mockk()
        val errorMessage = "Failed to load players"
        coEvery { getFollowedPlayersUseCase() } returns flow { throw RuntimeException(errorMessage) }
        viewModel = FollowedPlayersViewModel(getFollowedPlayersUseCase, unFollowPlayerUseCase)

        testDispatcher.scheduler.advanceTimeBy(6_000)
        testDispatcher.scheduler.advanceUntilIdle()
        val states = mutableListOf<FollowedPlayersUiState>()
        viewModel.uiState.take(2).toList(states)

        viewModel.viewModelScope.cancel()

        println("Collected states: $states")
        assertTrue(
            states.any { it is FollowedPlayersUiState.Error },
            "State should include Error when use case fails"
        )
        val lastState = states.last()
        assertTrue(
            lastState is FollowedPlayersUiState.Error,
            "Last state should be Error when use case fails"
        )
        assertEquals(
            errorMessage,
            lastState.message,
            "Error message should match expected"
        )
    }

    @Test
    fun `unfollow calls UnFollowPlayerUseCase with correct player`() = runTest {
        coEvery { unFollowPlayerUseCase(samplePlayer) } returns Unit

        viewModel.unfollow(samplePlayer)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { unFollowPlayerUseCase(samplePlayer) }
    }

    @Test
    fun `unfollow handles exception from UnFollowPlayerUseCase`() = runTest {
        coEvery { unFollowPlayerUseCase(samplePlayer) } throws RuntimeException("Unfollow failed")

        viewModel.unfollow(samplePlayer)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { unFollowPlayerUseCase(samplePlayer) }
    }
}