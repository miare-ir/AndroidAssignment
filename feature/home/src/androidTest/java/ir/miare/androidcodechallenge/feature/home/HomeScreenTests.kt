package ir.miare.androidcodechallenge.feature.home

import androidx.compose.foundation.lazy.rememberLazyListState
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
import ir.miare.androidcodechallenge.core.model.SortOption
import ir.miare.androidcodechallenge.core.model.Team
import ir.miare.androidcodechallenge.core.ui.PlayerCard
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeScreenTests {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testHomeScreenLoadingState() {
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    uiState = HomeUiState.Loading,
                    sortOption = SortOption.NONE,
                    pagingData = demoPagingItems(),
                    listState = rememberLazyListState(),
                    onSortBarClick = {},
                    onFollowClick = {}
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
    fun testHomeScreenEmptyState() {
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    uiState = HomeUiState.Empty,
                    sortOption = SortOption.NONE,
                    pagingData = demoEmptyPagingItems(),
                    listState = rememberLazyListState(),
                    onSortBarClick = {},
                    onFollowClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("There is no data to show").assertIsDisplayed()
    }

    @Test
    fun testHomeScreenErrorState() {
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    uiState = HomeUiState.Error("Test error message"),
                    sortOption = SortOption.NONE,
                    pagingData = demoEmptyPagingItems(),
                    listState = rememberLazyListState(),
                    onSortBarClick = {},
                    onFollowClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Test error message").assertIsDisplayed()
    }

    @Test
    fun testHomeScreenSuccessState() {
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    uiState = HomeUiState.Success(emptyList()),
                    sortOption = SortOption.NONE,
                    pagingData = demoPagingItems(),
                    listState = rememberLazyListState(),
                    onSortBarClick = {},
                    onFollowClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Tap to sort").assertIsDisplayed()
    }

    @Test
    fun testSortBarClick() {
        var clicked = false
        composeTestRule.setContent {
            MaterialTheme {
                SortBar(
                    onSortBarClick = { clicked = true },
                    sortOption = SortOption.NONE
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Tap to sort").performClick()
        assertTrue(clicked)
    }

    @Test
    fun testPlayerCardFollowClick() {
        var followed = false
        composeTestRule.setContent {
            MaterialTheme {
                PlayerCard(
                    item = Player("Test Player", Team("Test Team", 1), 10),
                    onFollowClick = { followed = true }
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Follow").performClick()
        assertTrue(followed)
    }

    @Test
    fun testHeaderCardDisplay() {
        composeTestRule.setContent {
            MaterialTheme {
                LeagueHeaderCard("Test League")
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Test League").assertIsDisplayed()
    }

    @Test
    fun testErrorStateRetryClick() {
        var retried = false
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    uiState = HomeUiState.Error("Test error message"),
                    sortOption = SortOption.NONE,
                    pagingData = demoPagingItems(),
                    listState = rememberLazyListState(),
                    onSortBarClick = {},
                    onFollowClick = {},
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Refresh").performClick()
        assertTrue(retried)
    }
}