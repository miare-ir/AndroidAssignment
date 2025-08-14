package ir.miare.androidcodechallenge.data.repository

import ir.miare.androidcodechallenge.data.remote.ApiService
import ir.miare.androidcodechallenge.data.remote.FakeDataResponse
import ir.miare.androidcodechallenge.data.remote.LeagueResponse
import ir.miare.androidcodechallenge.data.remote.PlayerResponse
import ir.miare.androidcodechallenge.data.remote.TeamResponse
import ir.miare.androidcodechallenge.domain.entity.Player
import ir.miare.androidcodechallenge.domain.entity.Team
import ir.miare.androidcodechallenge.domain.entity.League
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

@RunWith(MockitoJUnitRunner::class)
class PlayersRepositoryImplTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var playersRepository: PlayersRepositoryImpl

    @Before
    fun setup() {
        playersRepository = PlayersRepositoryImpl(mockApiService)
    }

    @Test
    fun `fetchAllLeagues should transform API response correctly`() = runTest {
        val mockResponse = listOf(
            FakeDataResponse(
                league = LeagueResponse("La Liga", "Spain", 2, 38),
                players = emptyList()
            )
        )
        `when`(mockApiService.getData()).thenReturn(mockResponse)

        val result = playersRepository.fetchAllLeagues()

        assertEquals(1, result.size)
        val laLiga = result[0]
        assertEquals("La Liga", laLiga.name)
        assertEquals("Spain", laLiga.country)
        assertEquals(2, laLiga.rank)
        assertEquals(38, laLiga.totalMatches)
        verify(mockApiService).getData()
    }

    @Test
    fun `fetchAllPlayers should transform API response correctly`() = runTest {
        val mockResponse = listOf(
            FakeDataResponse(
                league = LeagueResponse("La Liga", "Spain", 2, 38),
                players = listOf(
                    PlayerResponse("Jude Bellingham", TeamResponse("Real Madrid", 1), 19)
                )
            )
        )
        `when`(mockApiService.getData()).thenReturn(mockResponse)

        val result = playersRepository.fetchAllPlayers()

        assertEquals(1, result.size)
        val player = result[0]
        assertEquals("Jude Bellingham", player.name)
        assertEquals("Real Madrid", player.team.name)
        assertEquals(1, player.team.rank)
        assertEquals("La Liga", player.league.name)
        assertEquals(19, player.totalGoal)
        verify(mockApiService).getData()
    }
}
