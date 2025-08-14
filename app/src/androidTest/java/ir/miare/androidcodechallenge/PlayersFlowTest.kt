package ir.miare.androidcodechallenge

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.miare.androidcodechallenge.presentation.ui.AppRoot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PlayersFlowTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationBetweenTabs() {
        composeTestRule.onNodeWithText("Followed").performClick()
        
        composeTestRule.onNodeWithText("Followed").assertIsSelected()
        composeTestRule.onNodeWithText("All").assertIsNotSelected()
        
        composeTestRule.onNodeWithText("All").performClick()
        
        composeTestRule.onNodeWithText("All").assertIsSelected()
        composeTestRule.onNodeWithText("Followed").assertIsNotSelected()
    }

    @Test
    fun testSortChipsInteraction() {
        composeTestRule.onNodeWithText("A-Z").assertIsDisplayed()
        composeTestRule.onNodeWithText("Team & League Rank").assertIsDisplayed()
        composeTestRule.onNodeWithText("Most Goals").assertIsDisplayed()
        
        composeTestRule.onNodeWithText("Most Goals").performClick()
        composeTestRule.onNodeWithText("Team & League Rank").performClick()
        composeTestRule.onNodeWithText("A-Z").performClick()
        
        composeTestRule.onNodeWithText("A-Z").assertIsDisplayed()
    }

    @Test
    fun testPlayerListDisplay() {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("follow_button").fetchSemanticsNodes().isNotEmpty()
        }
        
        composeTestRule.onAllNodesWithTag("follow_button").assertAny(hasTestTag("follow_button"))
        
        composeTestRule.onNodeWithText("A-Z").assertIsDisplayed()
    }
}
