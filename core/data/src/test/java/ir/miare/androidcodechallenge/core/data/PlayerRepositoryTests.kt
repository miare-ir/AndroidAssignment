package ir.miare.androidcodechallenge.core.data

import io.mockk.coEvery
import io.mockk.mockk
import ir.miare.androidcodechallenge.core.data.repository.DefaultPlayerRepository
import ir.miare.androidcodechallenge.core.database.dao.FollowedPlayerDao
import ir.miare.androidcodechallenge.core.database.entity.FollowedPlayerEntity
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerRepositoryTests {

    private lateinit var repository: DefaultPlayerRepository
    private lateinit var followedPlayerDao: FollowedPlayerDao
    private val testDispatcher = StandardTestDispatcher()

    private val player = Player(
        name = "Erling Haaland",
        team = Team(name = "Manchester City", rank = 1),
        totalGoal = 27,
        isFollowed = true
    )
    val playerEntity = FollowedPlayerEntity(
        stableKey = "erling_haaland_manchester_city",
        playerName = "Erling Haaland",
        totalGoal = 27,
        teamName = "Manchester City",
        teamRank = 1
    )

    private val playerList = listOf(player)
    private val playerEntityList = listOf(playerEntity)
    private val playerKey = "erling_haaland"

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        followedPlayerDao = mockk()
        repository = DefaultPlayerRepository(followedPlayerDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getFollowedPlayers emits list of players from DAO`() = runTest {
        coEvery { followedPlayerDao.getAllPlayers() } returns flowOf(playerEntityList)
        val results = repository.getFollowedPlayers().toList()

        println("Collected results: $results")
        assertEquals(1, results.size, "Should emit one list of players")
        assertEquals(playerList, results[0], "Emitted players should match expected")
    }

    @Test
    fun `observeFollowed emits set of followed keys`() = runTest {
        val keys = listOf(playerKey)
        coEvery { followedPlayerDao.observeFollowedKeys() } returns flowOf(keys)
        val results = repository.observeFollowed().toList()

        println("Collected results: $results")
        assertEquals(1, results.size, "Should emit one set of keys")
        assertEquals(setOf(playerKey), results[0], "Emitted keys should match expected")
    }

    @Test
    fun `isFollowed returns true when key exists`() = runTest {
        coEvery { followedPlayerDao.exists(playerKey) } returns true
        val result = repository.isFollowed(playerKey)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(result, "isFollowed should return true when key exists")
    }

    @Test
    fun `isFollowed returns false when key does not exist`() = runTest {
        coEvery { followedPlayerDao.exists(playerKey) } returns false
        val result = repository.isFollowed(playerKey)
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(result, "isFollowed should return false when key does not exist")
    }
}