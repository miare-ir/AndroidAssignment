package ir.miare.androidcodechallenge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class PlayerFlowsTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun follow_player_shows_in_followed_screen() {
        // Wait for content
        composeRule.onNodeWithText("Sorting by:").assertIsDisplayed()

        // Click Follow on first visible player (button text toggles)
        composeRule.onAllNodesWithText("Follow").onFirst().performClick()

        // Navigate to Followed
        composeRule.onNodeWithText("Followed").performClick()

        // Expect followed list to show Unfollow button or player card
        composeRule.onNodeWithText("Unfollow").assertIsDisplayed()
    }
}


