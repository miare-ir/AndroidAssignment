package ir.miare.androidcodechallenge.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
    }

    @Test
    fun firstLeagueHeader_isDisplayed() {
        // Step 1: Get the first fake league header text
        val expectedHeaderText = "Serie A - Italy"

        // Step 2: Wait for that text to appear
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText(expectedHeaderText).fetchSemanticsNodes().isNotEmpty()
        }

        // Step 3: Assert it's visible
        composeTestRule
            .onNodeWithText(expectedHeaderText)
            .assertIsDisplayed()
    }


    @Test
    fun playerItemClick_opensBottomSheet() {
        // Wait until content is loaded
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithTag("PlayerItem").fetchSemanticsNodes().isNotEmpty()
        }

        // Click on the first player item
        composeTestRule
            .onAllNodesWithTag("PlayerItem")
            .onFirst()
            .performClick()

        // Check if bottom sheet with player name is shown
        composeTestRule
            .onNodeWithText("Back")
            .assertIsDisplayed()
    }
}
