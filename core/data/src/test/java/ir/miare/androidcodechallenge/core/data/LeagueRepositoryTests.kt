package ir.miare.androidcodechallenge.core.data

import io.mockk.coEvery
import io.mockk.mockk
import ir.miare.androidcodechallenge.core.data.repository.DefaultLeagueRepository
import ir.miare.androidcodechallenge.core.model.FakeData
import ir.miare.androidcodechallenge.core.model.League
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.Team
import ir.miare.androidcodechallenge.core.network.NetworkDataSource
import ir.miare.androidcodechallenge.core.network.model.NetworkFakeData
import ir.miare.androidcodechallenge.core.network.model.NetworkLeague
import ir.miare.androidcodechallenge.core.network.model.NetworkPlayer
import ir.miare.androidcodechallenge.core.network.model.NetworkTeam
import ir.miare.androidcodechallenge.core.network.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class LeagueRepositoryTests {

    private lateinit var repository: DefaultLeagueRepository
    private lateinit var networkDataSource: NetworkDataSource
    private val testDispatcher = StandardTestDispatcher()

    val networkFakeData = NetworkFakeData(
        league = NetworkLeague(
            name = "La Liga",
            country = "Spain",
            rank = 2,
            totalMatches = 38
        ),
        players = listOf(
            NetworkPlayer(
                name = "Jude Bellingham",
                team = NetworkTeam(
                    name = "Real Madrid",
                    rank = 1
                ),
                totalGoal = 19
            ),
            NetworkPlayer(
                name = "Robert Lewandowski",
                team = NetworkTeam(
                    name = "Barcelona",
                    rank = 2
                ),
                totalGoal = 23
            ),
            NetworkPlayer(
                name = "Antoine Griezmann",
                team = NetworkTeam(
                    name = "Atletico Madrid",
                    rank = 3
                ),
                totalGoal = 21
            ),
            NetworkPlayer(
                name = "Takefusa Kubo",
                team = NetworkTeam(
                    name = "Real Sociedad",
                    rank = 4
                ),
                totalGoal = 15
            ),
            NetworkPlayer(
                name = "Iago Aspas",
                team = NetworkTeam(
                    name = "Celta Vigo",
                    rank = 10
                ),
                totalGoal = 12
            )
        )
    )

    val fakeData = FakeData(
        league = League(
            name = "La Liga",
            country = "Spain",
            rank = 2,
            totalMatches = 38
        ),
        players = listOf(
            Player(
                name = "Jude Bellingham",
                team = Team(
                    name = "Real Madrid",
                    rank = 1
                ),
                totalGoal = 19
            ),
            Player(
                name = "Robert Lewandowski",
                team = Team(
                    name = "Barcelona",
                    rank = 2
                ),
                totalGoal = 23
            ),
            Player(
                name = "Antoine Griezmann",
                team = Team(
                    name = "Atletico Madrid",
                    rank = 3
                ),
                totalGoal = 21
            ),
            Player(
                name = "Takefusa Kubo",
                team = Team(
                    name = "Real Sociedad",
                    rank = 4
                ),
                totalGoal = 15
            ),
            Player(
                name = "Iago Aspas",
                team = Team(
                    name = "Celta Vigo",
                    rank = 10
                ),
                totalGoal = 12
            )
        )
    )
    private val networkFakeDataList = listOf(networkFakeData)
    private val fakeDataList = listOf(fakeData)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        networkDataSource = mockk()
        repository = DefaultLeagueRepository(networkDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getHome emits Success with mapped FakeData when network call succeeds`() = runTest {
        coEvery { networkDataSource.getHome() } returns ApiResult.Success(networkFakeDataList)

        val results = repository.getHome().toList()

        println("Collected results: $results")
        assertTrue(
            results.any { it is ApiResult.Success },
            "Result should include Success when network call succeeds"
        )
        val lastResult = results.last()
        assertTrue(lastResult is ApiResult.Success, "Last result should be Success")
        assertEquals(
            fakeDataList,
            lastResult.data,
            "FakeData list should match expected"
        )
    }

    @Test
    fun `getHome emits Error with throwable when network call fails`() = runTest {
        val throwable = RuntimeException("Network error")
        coEvery { networkDataSource.getHome() } returns ApiResult.Error(throwable)

        val results = repository.getHome().toList()

        println("Collected results: $results")
        assertTrue(
            results.any { it is ApiResult.Error },
            "Result should include Error when network call fails"
        )
        val lastResult = results.last()
        assertTrue(lastResult is ApiResult.Error, "Last result should be Error")
        assertEquals(
            throwable,
            lastResult.throwable,
            "Throwable should match expected"
        )
    }
}