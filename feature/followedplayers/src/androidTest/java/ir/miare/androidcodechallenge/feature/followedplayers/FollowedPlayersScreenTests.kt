package ir.miare.androidcodechallenge.feature.followedplayers

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.miare.androidcodechallenge.core.model.Player
import ir.miare.androidcodechallenge.core.model.Team
import ir.miare.androidcodechallenge.core.ui.PlayerCard
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FollowedPlayersScreenTests {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testFollowedPlayersScreenLoadingState() {
        composeTestRule.setContent {
            MaterialTheme {
                FollowedPlayersScreen(
                    uiState = FollowedPlayersUiState.Loading,
                    onUnfollowClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        try {
            composeTestRule.onNodeWithTag("Loading Section").assertIsDisplayed()
        } catch (e: AssertionError) {
            composeTestRule.onRoot().printToLog("UI_HIERARCHY")
            throw e
        }
    }

    @Test
    fun testFollowedPlayersScreenEmptyState() {
        composeTestRule.setContent {
            MaterialTheme {
                FollowedPlayersScreen(
                    uiState = FollowedPlayersUiState.Empty,
                    onUnfollowClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("You haven't followed any players yet.").assertIsDisplayed()
    }

    @Test
    fun testFollowedPlayersScreenErrorState() {
        val errorMessage = "Failed to load followed players"
        composeTestRule.setContent {
            MaterialTheme {
                FollowedPlayersScreen(
                    uiState = FollowedPlayersUiState.Error(Throwable(),errorMessage),
                    onUnfollowClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun testFollowedPlayersScreenSuccessState() {
        val players = listOf(
            Player("Test Player", Team("Test Team", 1), 10)
        )
        composeTestRule.setContent {
            MaterialTheme {
                FollowedPlayersScreen(
                    uiState = FollowedPlayersUiState.Success(players),
                    onUnfollowClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Test Player").assertIsDisplayed()
    }

    @Test
    fun testPlayerCardUnfollowClick() {
        var unfollowed = false
        val player = Player("Test Player", Team("Test Team", 1), 10)
        composeTestRule.setContent {
            MaterialTheme {
                PlayerCard(
                    item = player,
                    onFollowClick = { unfollowed = true }
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Follow").performClick()
        assertTrue(unfollowed)
    }

    @Test
    fun testTitleDisplay() {
        composeTestRule.setContent {
            MaterialTheme {
                FollowedPlayersScreen(
                    uiState = FollowedPlayersUiState.Empty,
                    onUnfollowClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Followed Players").assertIsDisplayed()
    }
}