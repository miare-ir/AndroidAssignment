package ir.miare.androidcodechallenge.presentation

import ir.miare.androidcodechallenge.domain.model.PlayerId
import ir.miare.androidcodechallenge.domain.model.PlayerWithContext
import ir.miare.androidcodechallenge.domain.repo.FollowRepository
import ir.miare.androidcodechallenge.domain.repo.PlayersRepository
import ir.miare.androidcodechallenge.domain.usecase.GetAllPlayers
import ir.miare.androidcodechallenge.domain.usecase.ObserveFollowed
import ir.miare.androidcodechallenge.domain.usecase.ToggleFollow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayersViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private class FakePlayersRepository(private val list: List<PlayerWithContext>) : PlayersRepository {
        override suspend fun getAllPlayers(): List<PlayerWithContext> = list
    }

    private class FakeFollowRepository(initial: Set<PlayerId> = emptySet()) : FollowRepository {
        private val state = MutableStateFlow(initial)
        override fun observeFollowed(): Flow<Set<PlayerId>> = state
        override suspend fun toggleFollow(playerId: PlayerId) {
            val set = state.value.toMutableSet()
            if (!set.add(playerId)) set.remove(playerId)
            state.value = set
        }
    }

    @Test
    fun initial_state_loads_players() = runTest(testDispatcher) {
        val players = listOf(
            PlayerWithContext(PlayerId("1"), "A", 10, "T1", 1, "L1", "C", 1, 38)
        )
        val repo = FakePlayersRepository(players)
        val follow = FakeFollowRepository()
        val vm = PlayersViewModel(
            getAllPlayers = GetAllPlayers(repo),
            observeFollowed = ObserveFollowed(follow),
            toggleFollow = ToggleFollow(follow)
        )
        advanceUntilIdle()
        val state = vm.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals("A", state.items.firstOrNull()?.name)
    }
}


