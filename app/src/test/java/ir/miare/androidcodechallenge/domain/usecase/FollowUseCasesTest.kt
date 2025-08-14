package ir.miare.androidcodechallenge.domain.usecase

import ir.miare.androidcodechallenge.domain.repository.FollowRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
class FollowUseCasesTest {

    @Mock
    private lateinit var mockRepository: FollowRepository

    private lateinit var observeFollowedUseCase: ObserveFollowedUseCase
    private lateinit var toggleFollowUseCase: ToggleFollowUseCase

    @Before
    fun setup() {
        observeFollowedUseCase = ObserveFollowedUseCase(mockRepository)
        toggleFollowUseCase = ToggleFollowUseCase(mockRepository)
    }

    @Test
    fun `ObserveFollowedUseCase should return repository flow`() = runTest {
        val expectedFollowedSet = setOf("Jude Bellingham", "Robert Lewandowski")
        `when`(mockRepository.observeFollowedPlayers()).thenReturn(flowOf(expectedFollowedSet))

        val result = observeFollowedUseCase().first()

        assertEquals(expectedFollowedSet, result)
        verify(mockRepository).observeFollowedPlayers()
    }

    @Test
    fun `ToggleFollowUseCase should call repository toggleFollow`() = runTest {
        val playerName = "Jude Bellingham"
        `when`(mockRepository.toggleFollow(playerName)).thenReturn(Unit)

        toggleFollowUseCase(playerName)

        verify(mockRepository).toggleFollow(playerName)
    }
}
