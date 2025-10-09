package ir.miare.androidcodechallenge.core.domain

import io.mockk.coEvery
import io.mockk.mockk
import ir.miare.androidcodechallenge.core.model.FakeData
import ir.miare.androidcodechallenge.core.model.League
import ir.miare.androidcodechallenge.core.model.LeagueDisplayItem
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.SortOption
import ir.miare.androidcodechallenge.core.model.Team
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetSortedLeagueDisplayItemsUseCaseTests {

    private lateinit var useCase: GetSortedLeagueDisplayItemsUseCase
    private lateinit var leagueRepository: LeagueRepository
    private val testDispatcher = StandardTestDispatcher()

    private val team1 = Team(name = "Manchester City", rank = 1)
    private val team2 = Team(name = "Liverpool", rank = 2)
    private val team3 = Team(name = "Barcelona", rank = 3)
    private val league1 = League(name = "Premier League", rank = 1, country = "England", totalMatches = 38)
    private val league2 = League(name = "La Liga", rank = 2, country = "Spain", totalMatches = 38)
    private val player1 = Player(name = "Erling Haaland", team = team1, totalGoal = 27, isFollowed = false)
    private val player2 = Player(name = "Mohamed Salah", team = team2, totalGoal = 20, isFollowed = false)
    private val player3 = Player(name = "Lionel Messi", team = team3, totalGoal = 25, isFollowed = false)
    private val fakeData1 = FakeData(league = league1, players = listOf(player1, player2))
    private val fakeData2 = FakeData(league = league2, players = listOf(player3))
    private val fakeDataList = listOf(fakeData1, fakeData2)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        leagueRepository = mockk()
        useCase = GetSortedLeagueDisplayItemsUseCase(leagueRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke with TEAM_AND_LEAGUE_RANK sorts leagues by rank and players by team rank`() = runTest {
        coEvery { leagueRepository.getHome() } returns flowOf(ApiResult.Success(fakeDataList))

        val results = useCase(SortOption.TEAM_AND_LEAGUE_RANK).toList()

        assertEquals(1, results.size, "Should emit one result")
        assertTrue(results[0] is ApiResult.Success, "Result should be Success")
        val displayItems = (results[0] as ApiResult.Success).data
        assertEquals(5, displayItems.size, "Should have two headers and three player items")
        assertEquals(LeagueDisplayItem.Header(league1), displayItems[0], "First item should be Premier League header (rank 1)")
        assertEquals(LeagueDisplayItem.PlayerItem(player1, league1, null), displayItems[1], "Second item should be Haaland (team rank 1)")
        assertEquals(LeagueDisplayItem.PlayerItem(player2, league1, null), displayItems[2], "Third item should be Salah (team rank 2)")
        assertEquals(LeagueDisplayItem.Header(league2), displayItems[3], "Fourth item should be La Liga header (rank 2)")
        assertEquals(LeagueDisplayItem.PlayerItem(player3, league2, null), displayItems[4], "Fifth item should be Messi (team rank 3)")
        println("Display items: $displayItems")
    }

    @Test
    fun `invoke with MOST_GOALS sorts players by total goals descending`() = runTest {
        coEvery { leagueRepository.getHome() } returns flowOf(ApiResult.Success(fakeDataList))

        val results = useCase(SortOption.MOST_GOALS).toList()

        assertEquals(1, results.size, "Should emit one result")
        assertTrue(results[0] is ApiResult.Success, "Result should be Success")
        val displayItems = (results[0] as ApiResult.Success).data
        assertEquals(3, displayItems.size, "Should have three player items, no headers")
        assertEquals(LeagueDisplayItem.PlayerItem(player1, league1, null), displayItems[0], "First item should be Haaland (27 goals)")
        assertEquals(LeagueDisplayItem.PlayerItem(player3, league2, null), displayItems[1], "Second item should be Messi (25 goals)")
        assertEquals(LeagueDisplayItem.PlayerItem(player2, league1, null), displayItems[2], "Third item should be Salah (20 goals)")
        println("Display items: $displayItems")
    }

    @Test
    fun `invoke with AVERAGE_GOALS_PER_MATCH sorts leagues by average goals per match`() = runTest {
        coEvery { leagueRepository.getHome() } returns flowOf(ApiResult.Success(fakeDataList))

        val results = useCase(SortOption.AVERAGE_GOALS_PER_MATCH).toList()

        assertEquals(1, results.size, "Should emit one result")
        assertTrue(results[0] is ApiResult.Success, "Result should be Success")
        val displayItems = (results[0] as ApiResult.Success).data
        assertEquals(2, displayItems.size, "Should have two league headers, no players")
        assertEquals(LeagueDisplayItem.Header(league1), displayItems[0], "First item should be Premier League header (47/38 goals per match)")
        assertEquals(LeagueDisplayItem.Header(league2), displayItems[1], "Second item should be La Liga header (25/38 goals per match)")
        println("Display items: $displayItems")
    }

    @Test
    fun `invoke with NONE preserves original order`() = runTest {
        coEvery { leagueRepository.getHome() } returns flowOf(ApiResult.Success(fakeDataList))

        val results = useCase(SortOption.NONE).toList()

        assertEquals(1, results.size, "Should emit one result")
        assertTrue(results[0] is ApiResult.Success, "Result should be Success")
        val displayItems = (results[0] as ApiResult.Success).data
        assertEquals(5, displayItems.size, "Should have two headers and three player items")
        assertEquals(LeagueDisplayItem.Header(league1), displayItems[0], "First item should be Premier League header")
        assertEquals(LeagueDisplayItem.PlayerItem(player1, league1, null), displayItems[1], "Second item should be Haaland")
        assertEquals(LeagueDisplayItem.PlayerItem(player2, league1, null), displayItems[2], "Third item should be Salah")
        assertEquals(LeagueDisplayItem.Header(league2), displayItems[3], "Fourth item should be La Liga header")
        assertEquals(LeagueDisplayItem.PlayerItem(player3, league2, null), displayItems[4], "Fifth item should be Messi")
        println("Display items: $displayItems")
    }

    @Test
    fun `invoke propagates error from repository`() = runTest {
        val error = ApiResult.Error(Exception("Network error"))
        coEvery { leagueRepository.getHome() } returns flowOf(error)

        val results = useCase(SortOption.NONE).toList()

        assertEquals(1, results.size, "Should emit one result")
        assertTrue(results[0] is ApiResult.Error, "Result should be Error")
        assertEquals("Network error", (results[0] as ApiResult.Error).throwable.message, "Error message should match")
        println("Error result: $results")
    }

    @Test
    fun `invoke catches exception and emits error`() = runTest {
        val exception = RuntimeException("Unexpected error")
        coEvery { leagueRepository.getHome() } returns flow { throw exception }

        val results = useCase(SortOption.NONE).toList()

        assertEquals(1, results.size, "Should emit one result")
        assertTrue(results[0] is ApiResult.Error, "Result should be Error")
        assertEquals("Unexpected error", (results[0] as ApiResult.Error).throwable.message, "Error message should match")
        println("Error result: $results")
    }
}