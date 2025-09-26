package ir.miare.androidcodechallenge.core.repository

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import ir.miare.androidcodechallenge.core.data.helper.FileReader
import ir.miare.androidcodechallenge.core.data.helper.SortSettings
import ir.miare.androidcodechallenge.core.data.repository.FootballRepositoryImpl
import ir.miare.androidcodechallenge.core.database.dao.LeagueDao
import ir.miare.androidcodechallenge.core.database.dao.PlayerDao
import ir.miare.androidcodechallenge.core.database.dao.TeamDao
import ir.miare.androidcodechallenge.core.model.SortMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FootballRepositoryImplTest {

    private val leagueDao: LeagueDao = mockk(relaxed = true)
    private val playerDao: PlayerDao = mockk(relaxed = true)
    private val teamDao: TeamDao = mockk(relaxed = true)
    private val fileReader: FileReader = mockk()
    private val sortSettings: SortSettings = mockk()

    private lateinit var repository: FootballRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { sortSettings.sortMode } returns flowOf(SortMode.DEFAULT)
        repository = FootballRepositoryImpl(
            leagueDao,
            playerDao,
            teamDao,
            fileReader,
            sortSettings,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ensureSeeded does nothing if players already exist`() = runTest {
        coEvery { playerDao.countPlayers() } returns 5

        repository.ensureSeeded()

        coVerify(exactly = 0) { fileReader.readeJsonFile() }
        coVerify(exactly = 0) { leagueDao.insertAll(any()) }
        coVerify(exactly = 0) { teamDao.insertAll(any()) }
        coVerify(exactly = 0) { playerDao.insertAll(any()) }
    }

    @Test
    fun `ensureSeeded reads file and inserts data when no players exist`() = runTest {
        coEvery { playerDao.countPlayers() } returns 0

        coEvery { fileReader.readeJsonFile() } returns fakeJson

        repository.ensureSeeded()

        coVerify {
            leagueDao.insertAll(match { leagues ->
                leagues.size == 1 && leagues.first().leagueName == "La Liga"
            })
        }

        coVerify {
            teamDao.insertAll(match { teams ->
                teams.map { it.teamName }.containsAll(
                    listOf(
                        "Real Madrid",
                        "Barcelona",
                        "Real Sociedad",
                        "Atletico Madrid",
                        "Celta Vigo"
                    )
                )
            })
        }

        coVerify {
            playerDao.insertAll(match { players ->
                players.map { it.playerName }.containsAll(
                    listOf(
                        "Jude Bellingham",
                        "Robert Lewandowski",
                        "Takefusa Kubo",
                        "Antoine Griezmann",
                        "Iago Aspas"
                    )
                )
            })
        }
    }

    @Test
    fun `setSortMode delegates to sortSettings`() = runTest {
        coEvery { sortSettings.setSortMode(SortMode.LEAGUE_RANK) } just Runs

        repository.setSortMode(SortMode.LEAGUE_RANK)

        coVerify { sortSettings.setSortMode(SortMode.LEAGUE_RANK) }
    }
}

private val fakeJson = """
            [
              {
                "league": {
                  "name": "La Liga",
                  "country": "Spain",
                  "rank": 2,
                  "total_matches": 38
                },
                "players": [
                  {
                    "name": "Jude Bellingham",
                    "total_goal": 19,
                    "team": {
                      "name": "Real Madrid",
                      "rank": 1
                    }
                  },
                  {
                    "name": "Robert Lewandowski",
                    "total_goal": 23,
                    "team": {
                      "name": "Barcelona",
                      "rank": 2
                    }
                  },
                  {
                    "name": "Takefusa Kubo",
                    "total_goal": 15,
                    "team": {
                      "name": "Real Sociedad",
                      "rank": 5
                    }
                  },
                  {
                    "name": "Antoine Griezmann",
                    "total_goal": 21,
                    "team": {
                      "name": "Atletico Madrid",
                      "rank": 3
                    }
                  },
                  {
                    "name": "Iago Aspas",
                    "total_goal": 16,
                    "team": {
                      "name": "Celta Vigo",
                      "rank": 4
                    }
                  }
                ]
              }
            ]
        """.trimIndent()



