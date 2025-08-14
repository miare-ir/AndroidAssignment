package ir.miare.androidcodechallenge.data.repository

import ir.miare.androidcodechallenge.data.local.FollowStore
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
class FollowRepositoryImplTest {

    @Mock
    private lateinit var mockFollowStore: FollowStore

    private lateinit var followRepository: FollowRepositoryImpl

    @Before
    fun setup() {
        followRepository = FollowRepositoryImpl(mockFollowStore)
    }

    @Test
    fun `observeFollowedPlayers should return store flow`() = runTest {
        val expectedFollowedSet = setOf("Jude Bellingham", "Robert Lewandowski")
        `when`(mockFollowStore.followedIds).thenReturn(flowOf(expectedFollowedSet))

        val result = followRepository.observeFollowedPlayers().first()

        assertEquals(expectedFollowedSet, result)
        verify(mockFollowStore).followedIds
    }

    @Test
    fun `toggleFollow should call store toggleFollow`() = runTest {
        val playerName = "Jude Bellingham"
        `when`(mockFollowStore.toggleFollow(playerName)).thenReturn(Unit)

        followRepository.toggleFollow(playerName)

        verify(mockFollowStore).toggleFollow(playerName)
    }
}
