package ir.miare.androidcodechallenge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AppTests {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    fun delay(mills: Long = 5000) {
        composeTestRule.waitForIdle()
        Thread.sleep(mills)
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ir.miare.androidcodechallenge.beta", appContext.packageName)
    }

    @Test
    fun testAppLaunchesSuccessfully() {
        composeTestRule.onNodeWithTag("Splash Screen")
            .assertExists()
            .assertIsDisplayed()
    }


    @Test
    fun testHomeScreenDisplayedAfterSplash() {
       delay()

        composeTestRule.onNodeWithContentDescription("TopAppBar Title Image")
            .assertExists()
            .assertIsDisplayed()
    }



    @Test
    fun testBottomNavigationBarIsDisplayed() {
        delay()
        composeTestRule.onNodeWithText("Home")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testBottomNavigationDestinations() {
        delay()
        val destinations = listOf("Followed", "Home")

        destinations.forEach { destination ->
            composeTestRule.onNodeWithText(destination)
                .assertExists()
                .assertIsDisplayed()
                .performClick()
        }
    }

    @Test
    fun testNavigationStateManagement() {
        delay()
        composeTestRule.onNodeWithText("Home").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Followed").performClick()
        composeTestRule.waitForIdle()
    }


    @Test
    fun testAppPerformance() {
        delay()
        val startTime = System.currentTimeMillis()
        repeat(10) {
            composeTestRule.onNodeWithText("Home").performClick()
            composeTestRule.waitForIdle()
        }

        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        assertTrue("App interactions took too long: ${duration}ms", duration < 10000)
    }
}