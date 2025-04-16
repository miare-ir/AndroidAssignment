package ir.miare.androidcodechallenge.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ir.miare.androidcodechallenge.core.presentation.UiState
import ir.miare.androidcodechallenge.core.presentation.UiText
import ir.miare.androidcodechallenge.presentation.models.UiLeague
import ir.miare.androidcodechallenge.presentation.models.UiPlayer
import ir.miare.androidcodechallenge.presentation.models.UiTeam
import ir.miare.androidcodechallenge.presentation.models.UiTopScorers
import ir.miare.androidcodechallenge.presentation.utils.Constants
import org.junit.Test
import org.junit.Rule

class TopScorersScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialStateIsLoading() {
        val state = TopScorersUiState()
        composeTestRule.setContent {
            TopScorersScreenContent(state, {})
        }
        composeTestRule.onNodeWithTag(Constants.LOADING_INDICATOR_TAG).assertIsDisplayed()
    }

    @Test
    fun successfulStateIsDisplaysData() {
        composeTestRule.setContent {
            TopScorersScreenContent(fakeState, {})
        }
        composeTestRule.onNodeWithTag(Constants.TOP_SCORERS_ORDER_SECTION).assertIsDisplayed()
        composeTestRule.onNodeWithTag(Constants.TOP_SCORERS_LIST_TAG).assertIsDisplayed()
    }

    @Test
    fun errorStateIsDisplaysErrorSection() {
        val errorMessage = "Error occurred"
        val errorState = TopScorersUiState(
            topScorers = UiState.Failure(
                error = UiText.DynamicString(value = errorMessage)
            )
        )
        composeTestRule.setContent {
            TopScorersScreenContent(errorState, {})
        }
        composeTestRule.onNodeWithTag(Constants.ERROR_MESSAGE_TAG).assertIsDisplayed()
    }

}

private val fakeScorers = listOf(
    UiTopScorers(
        league = UiLeague(
            name = "Premier League",
            country = "England",
            rank = 1,
            totalMatches = 38
        ),
        players = listOf(
            UiPlayer(
                name = "Player 1",
                team = UiTeam(
                    name = "Team A",
                    rank = 120
                ),
                totalGoal = 20
            ),
            UiPlayer(
                name = "Player 2",
                team = UiTeam(
                    name = "Team A",
                    rank = 89
                ),
                totalGoal = 20
            ),
        )
    )
)

private val fakeState = TopScorersUiState(
    topScorers = UiState.Success(
        data = fakeScorers
    )
)
